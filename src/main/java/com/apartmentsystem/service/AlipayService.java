package com.apartmentsystem.service;

import com.apartmentsystem.entity.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AlipayService {
    void handleAlipayCallback(HttpServletRequest request);
    Map<String, String> getParamsFromRequest(HttpServletRequest request);
    void createOrder(String outTradeNo, String totalAmount, String subject, String body);
}
