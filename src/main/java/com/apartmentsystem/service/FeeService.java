package com.apartmentsystem.service;

import com.alipay.api.AlipayApiException;
import com.apartmentsystem.entity.Fee;
import com.apartmentsystem.entity.Result;

import java.util.List;

public interface FeeService {
    Result insertFee(Fee fee) throws AlipayApiException;
    Result insertdepositFee(Fee fee) throws AlipayApiException;
    Result updateFeePaidById(int id);
    List<Fee> getFeeByHouseNumber(String house_number);
    Fee getFeeById(int id);
    boolean createAliPayOrderForm(Fee fee) throws AlipayApiException;
}
