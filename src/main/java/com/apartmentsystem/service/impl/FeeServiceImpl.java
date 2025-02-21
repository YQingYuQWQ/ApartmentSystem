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
import com.apartmentsystem.mapper.FeeMapper;
import com.apartmentsystem.prop.AlipayProperties;
import com.apartmentsystem.service.FeeService;
import com.apartmentsystem.util.DateFormatUtil;
import com.apartmentsystem.util.UserHolder;
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
    @Autowired
    private HouseServiceImpl houseServiceImpl;
    @Autowired
    private LeaseContractServiceImpl leaseContractServiceImpl;
    @Autowired
    private LogServiceImpl logServiceImpl;

    @Override
    public String insertFee(Fee fee) throws AlipayApiException {
        // 生成订单号
        fee.setFee_number(generateOrderNumber("Fee"));
        if(fee.getType().equals("deposit")){
            houseServiceImpl.updateStatusByHouseNumber(fee.getHouse_number(), "booked");
        }
        feeMapper.insertFee(fee);
        createAliPayOrderForm(fee);
        return fee.getFee_number();
    }

    @Override
    public String insertdepositFee(Fee fee) throws AlipayApiException {
        if(!"vacant".equals(houseServiceImpl.getStatusByHouseNumber(fee.getHouse_number())))
            throw new RuntimeException("房屋已被预定");
        if(leaseContractServiceImpl.selectActiveLeaseContractByUserId(UserHolder.getUser().getId()) != null)
            throw new RuntimeException("您已经有租赁合同");

        fee.setFee_number(generateOrderNumber("Fee"));
        fee.setType("deposit");
        fee.setAmount(houseServiceImpl.getHouseByHouseNumber(fee.getHouse_number()).getDeposit());

        houseServiceImpl.updateStatusByHouseNumber(fee.getHouse_number(), "booked");
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "预定房屋" + fee.getHouse_number());
        feeMapper.insertFee(fee);
        createAliPayOrderForm(fee);
        if (!createAliPayOrderForm(fee))
            throw new RuntimeException("支付宝订单生成失败");
        return fee.getFee_number();
    }

    @Override
    public void updateFeePaidById(int id) {
        feeMapper.updateFeePaidById(id);
    }

    @Override
    public void updateFeeStatusByFeeNumber(String fee_number, boolean status) {
        feeMapper.updateFeeStatusByFeeNumber(fee_number, status);
        switch (feeMapper.getFeeByFeeNumber(fee_number).getType()){
            case "deposit":
                houseServiceImpl.updateStatusByHouseNumber(feeMapper.getFeeByFeeNumber(fee_number).getHouse_number(), "occupied");
                break;
            case "rent":
            case "water":
            case "power":
            case "utilities":
            case "maintenance":
            default:
                throw new RuntimeException("费用类型错误");
        }
    }

    @Override
    public List<Fee> getFeeByHouseNumber(String house_number) {
        return feeMapper.getFeeByHouseNumber(house_number);
    }

    @Override
    public Fee getFeeById(int id) {
        return feeMapper.getFeeById(id);
    }

    @Override
    public boolean createAliPayOrderForm(Fee fee) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        request.setBizModel(model);
        model.setOutTradeNo(fee.getFee_number());
        model.setTotalAmount(fee.getAmount().toString());
        String feeType = fee.getType();
        switch (feeType) {
            case "rent":
                model.setSubject("公寓管理系统-缴费-房租");
                break;
            case "water":
                model.setSubject("公寓管理系统-缴费-水费");
                break;
            case "power":
                model.setSubject("公寓管理系统-缴费-电费");
                break;
            case "deposit":
                model.setSubject("公寓管理系统-缴费-押金");
                break;
            case "utilities":
                model.setSubject("公寓管理系统-缴费-物业费");
                break;
            case "maintenance":
                model.setSubject("公寓管理系统-缴费-维修费");
                break;
            default:
                throw new RuntimeException("费用类型错误");
        }
        fee.setDue_date(feeMapper.getFeeById(fee.getId()).getDue_date());
        model.setTimeExpire(DateFormatUtil.formatDate(fee.getDue_date()));
        model.setBody(fee.getFee_number());
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        QrCodeUtil.generate(response.getQrCode(), 500, 500, FileUtil.file("E:/QrCode/"+ fee.getFee_number() +".jpg"));
        return true;
    }
}
