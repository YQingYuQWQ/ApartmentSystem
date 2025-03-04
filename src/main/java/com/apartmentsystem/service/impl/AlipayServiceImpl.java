package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.Fee;
import com.apartmentsystem.service.AlipayService;
import com.apartmentsystem.util.AlipayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlipayServiceImpl implements AlipayService {
    @Autowired
    private FeeServiceImpl feeServiceImpl;
    @Autowired
    private HouseServiceImpl houseServiceImpl;

    /**
     * 处理支付宝回调
     */
    public void handleAlipayCallback(HttpServletRequest request) {
        // 1. 获取支付宝回调参数
        Map<String, String> params = getParamsFromRequest(request);

        // 2. 进行验签（调用 AlipayUtil）
        if (!AlipayUtil.verifySignature(params)) {
            System.err.println("支付宝回调验签失败！");
            throw new RuntimeException("支付宝回调验签失败！");
        }

        System.out.println("支付宝回调验签成功！");

//        String outTradeNo = params.get("out_trade_no"); // 订单号
//        String tradeNo = params.get("trade_no"); // 支付宝交易号
        String tradeStatus = params.get("trade_status"); // 交易状态
        if (!"TRADE_SUCCESS".equals(tradeStatus))
            throw new RuntimeException("支付宝回调交易状态不是TRADE_SUCCESS！");

        String fee_number = params.get("body");
        System.out.println("支付宝回调成功！订单号：" + fee_number);
        Fee fee = new Fee();
        fee.setType(feeServiceImpl.getTypeByFeeNumber(fee_number).getType());
        fee.setUser_id(feeServiceImpl.getUserIdByFeeNumber(fee_number).getUser_id());
        fee.setHouse_number(feeServiceImpl.getHouseNumberByFeeNumber(fee_number).getHouse_number());
        switch (fee.getType()) {
            case "deposit":
                houseServiceImpl.updateOwnerByHouseNumber(fee.getHouse_number(), fee.getUser_id());
                feeServiceImpl.updateFeePaidByFeeNumber(fee_number, true);
                break;
            case "power":
                houseServiceImpl.updatePowerFeeByHouseNumber(fee.getHouse_number(), fee.getAmount());
                feeServiceImpl.updateFeePaidByFeeNumber(fee_number, true);
                feeServiceImpl.updateFeeStatusByFeeNumber(fee_number, true);
                break;
            case "water":
                houseServiceImpl.updateWaterFeeByHouseNumber(fee.getHouse_number(), fee.getAmount());
                feeServiceImpl.updateFeePaidByFeeNumber(fee_number, true);
                feeServiceImpl.updateFeeStatusByFeeNumber(fee_number, true);
                break;
            case "maintenance":
                feeServiceImpl.updateFeePaidByFeeNumber(fee_number, true);
                break;
            case "utilities":
                feeServiceImpl.updateFeePaidByFeeNumber(fee_number, true);
                break;
            case "rent":
                feeServiceImpl.updateFeePaidByFeeNumber(fee_number, true);
                break;
        }
    }

    /**
     * 获取支付宝回调参数
     */
    public Map<String, String> getParamsFromRequest(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> params.put(key, values[0]));
        return params;
    }
}
