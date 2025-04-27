package com.apartmentsystem.service;

import com.alipay.api.AlipayApiException;
import com.apartmentsystem.entity.Fee;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface FeeService {
    String insertFee(Fee fee) throws AlipayApiException;
    String insertDepositFee(Fee fee) throws AlipayApiException;
    String insertPowerFee(Fee fee) throws AlipayApiException;
    String insertWaterFee(Fee fee) throws AlipayApiException;
    String insertMaintenanceFee(Fee fee) throws AlipayApiException;
    String insertUtilitiesFee(Fee fee) throws AlipayApiException;
    String insertRentFee(Fee fee) throws AlipayApiException;
    void updateFeePaidById(int id);
    void updateFeePaidByFeeNumber(String fee_number, boolean paid);
    void updateFeeStatusByFeeNumber(String fee_number, boolean status);
    List<Fee> getFeeByHouseNumber(String house_number);
    List<Fee> getFeeByUserId(int user_id);
    List<Fee> getAllFee();
    String createAliPayOrderForm(Fee fee) throws AlipayApiException;
    Fee getFeeById(int id);
    Fee getFeeByFeeNumber(String fee_number);
    Fee getUserIdByFeeNumber(String fee_number);
    Fee getTypeByFeeNumber(String fee_number);
    Fee getHouseNumberByFeeNumber(String fee_number);
    Page<Fee> getFeeListWithPagination(String type, Integer paid, Integer status, int page, int size);

}
