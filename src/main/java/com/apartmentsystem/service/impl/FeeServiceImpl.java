package com.apartmentsystem.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.apartmentsystem.entity.Fee;
import com.apartmentsystem.entity.Result;
import com.apartmentsystem.mapper.FeeMapper;
import com.apartmentsystem.prop.AlipayProperties;
import com.apartmentsystem.service.FeeService;
import com.apartmentsystem.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.apartmentsystem.util.OrderNumberUtil.generateOrderNumber;

@Service
public class FeeServiceImpl implements FeeService{
    @Autowired
    private FeeMapper feeMapper;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private AlipayProperties alipayProperties;


    @Override
    public Result insertFee(Fee fee) throws AlipayApiException {
        fee.setFee_number(generateOrderNumber("Fee"));
        feeMapper.insertFee(fee);
        createAliPayOrderForm(fee);
        return Result.success("订单创建成功\n" + "订单编号：" + fee.getFee_number());
    }

    @Override
    public Result updateFeePaidById(int id) {
        feeMapper.updateFeePaidById(id);
        return Result.success("订单更新成功");
    }

    @Override
    public List<Fee> getFeeByHouseNumber(String house_number) {
        return feeMapper.getFeeByHouseNumber(house_number);
    }

    @Override
    public Fee getFeeById(int id) {
        return feeMapper.getFeeById(id);
    }

    public boolean createAliPayOrderForm(Fee fee) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        request.setBizModel(model);
        model.setOutTradeNo(fee.getFee_number());
        model.setTotalAmount(fee.getAmount().toString());
        model.setSubject("公寓管理系统-缴费");
        fee.setDue_date(feeMapper.getFeeById(fee.getId()).getDue_date());
        model.setTimeExpire(DateFormatUtil.formatDate(fee.getDue_date()));

        System.out.println(DateFormatUtil.formatDate(fee.getDue_date()));
        System.out.println(alipayProperties.getNotifyUrl());

        AlipayTradePrecreateResponse response = alipayClient.execute(request);

        System.out.println(response.getQrCode());

        QrCodeUtil.generate(response.getQrCode(), 500, 500, FileUtil.file("E:/QrCode/"+ fee.getFee_number() +".jpg"));
        return true;
    }
}
