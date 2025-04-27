package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.Fee;
import com.apartmentsystem.entity.HouseMonthlyBill;
import com.apartmentsystem.service.AlipayService;
import com.apartmentsystem.util.AlipayUtil;
import org.checkerframework.checker.units.qual.A;
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
    @Autowired
    private HouseMonthlyBillServiceImpl houseMonthlyBillServiceImpl;

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

//        String outTradeNo = params.get("out_trade_no"); // 订单号
//        String tradeNo = params.get("trade_no"); // 支付宝交易号
        String tradeStatus = params.get("trade_status"); // 交易状态
        if (!"TRADE_SUCCESS".equals(tradeStatus))
            throw new RuntimeException("支付宝回调交易状态不是TRADE_SUCCESS！");

        String fee_number = params.get("body");
        Fee fee = feeServiceImpl.getFeeByFeeNumber(fee_number);

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
                System.out.println("房租月账单id" + fee.getHouse_monthly_bill_id());
                feeServiceImpl.updateFeePaidByFeeNumber(fee_number, true);
                houseMonthlyBillServiceImpl.updateRent_statusById(fee.getHouse_monthly_bill_id(), 1);
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
