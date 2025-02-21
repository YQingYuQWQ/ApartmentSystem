package com.apartmentsystem.service;

import com.alipay.api.AlipayApiException;
import com.apartmentsystem.entity.Fee;

import java.util.List;

public interface FeeService {
    String insertFee(Fee fee) throws AlipayApiException;
    String insertdepositFee(Fee fee) throws AlipayApiException;
    void updateFeePaidById(int id);
    List<Fee> getFeeByHouseNumber(String house_number);
    Fee getFeeById(int id);
    boolean createAliPayOrderForm(Fee fee) throws AlipayApiException;
}
