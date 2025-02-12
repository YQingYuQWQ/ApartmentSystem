package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.AlipayService;
import com.apartmentsystem.util.AlipayUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlipayServiceImpl implements AlipayService {
    /**
     * 处理支付宝回调
     */
    public Result handleAlipayCallback(HttpServletRequest request) {
        // 1. 获取支付宝回调参数
        Map<String, String> params = getParamsFromRequest(request);

        // 2. 进行验签（调用 AlipayUtil）
        if (!AlipayUtil.verifySignature(params)) {
            System.err.println("支付宝回调验签失败！");
            return Result.error("支付宝回调验签失败！");
        }

        System.out.println("支付宝回调验签成功！");

        // 3. 获取订单信息
        String outTradeNo = params.get("out_trade_no"); // 订单号
        String tradeNo = params.get("trade_no"); // 支付宝交易号
        String tradeStatus = params.get("trade_status"); // 交易状态

        // 4. 处理订单业务逻辑
        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            return Result.success();
        }

        return Result.error("支付宝回调处理失败！");
    }

    /**
     * 获取支付宝回调参数
     */
    public Map<String, String> getParamsFromRequest(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> params.put(key, values[0]));
        return params;
    }

    @Override
    public void createOrder(String outTradeNo, String totalAmount, String subject, String body) {

    }

}
