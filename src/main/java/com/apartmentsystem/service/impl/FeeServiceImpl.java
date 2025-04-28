package com.apartmentsystem.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.apartmentsystem.entity.Fee;
import com.apartmentsystem.mapper.FeeMapper;
import com.apartmentsystem.mapper.UserMapper;
import com.apartmentsystem.prop.AlipayProperties;
import com.apartmentsystem.service.FeeService;
import com.apartmentsystem.util.DateFormatUtil;
import com.apartmentsystem.util.UserHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;

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
    public String insertDepositFee(Fee fee) throws AlipayApiException {
        if(!"vacant".equals(houseServiceImpl.getStatusByHouseNumber(fee.getHouse_number())))
            throw new RuntimeException("房屋已被预定");
        if(leaseContractServiceImpl.selectActiveLeaseContractByUserId(UserHolder.getUser().getId()) != null)
            throw new RuntimeException("您已经有租赁合同");
        if(null != houseServiceImpl.getHouseByOwnerId(UserHolder.getUser().getId()))
            throw new RuntimeException("您已经有房屋");

        fee.setFee_number(generateOrderNumber("Fee"));
        fee.setType("deposit");
        fee.setUser_id(UserHolder.getUser().getId());
        fee.setAmount(houseServiceImpl.getHouseByHouseNumber(fee.getHouse_number()).getDeposit());

        houseServiceImpl.updateStatusByHouseNumber(fee.getHouse_number(), "booked");
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "预定房屋" + fee.getHouse_number());
        feeMapper.insertFee(fee);
        return createAliPayOrderForm(fee);
    }

    @Override
    public String insertPowerFee(Fee fee) throws AlipayApiException {
        fee.setFee_number(generateOrderNumber("Fee"));
        fee.setType("power");
        feeMapper.insertFee(fee);
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "缴纳电费" + fee.getHouse_number());
        return createAliPayOrderForm(fee);
    }

    @Override
    public String insertWaterFee(Fee fee) throws AlipayApiException {
        fee.setFee_number(generateOrderNumber("Fee"));
        fee.setType("water");
        feeMapper.insertFee(fee);
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "缴纳水费" + fee.getHouse_number());
        return createAliPayOrderForm(fee);
    }

    @Override
    public String insertMaintenanceFee(Fee fee) throws AlipayApiException {
        fee.setFee_number(generateOrderNumber("Fee"));
        fee.setType("maintenance");
        feeMapper.insertFee(fee);
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "缴纳维修费" + fee.getHouse_number());
        return createAliPayOrderForm(fee);
    }

    @Override
    public String insertUtilitiesFee(Fee fee) throws AlipayApiException {
        fee.setFee_number(generateOrderNumber("Fee"));
        fee.setType("utilities");
        feeMapper.insertFee(fee);
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "缴纳物业费" + fee.getHouse_number());
        return createAliPayOrderForm(fee);
    }

    @Override
    public String insertRentFee(Fee fee) throws AlipayApiException {
        fee.setFee_number(generateOrderNumber("Fee"));
        fee.setType("rent");
        feeMapper.insertFee(fee);
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "缴纳房租" + fee.getHouse_number());
        return createAliPayOrderForm(fee);
    }

    @Override
    public void updateFeePaidById(int id) {
        feeMapper.updateFeePaidById(id);
    }

    @Override
    public void updateFeePaidByFeeNumber(String fee_number, boolean paid) {
        feeMapper.updateFeePaidByFeeNumber(fee_number, paid);
        switch (feeMapper.getFeeByFeeNumber(fee_number).getType()){
            case "deposit":
                houseServiceImpl.updateStatusByHouseNumber(feeMapper.getFeeByFeeNumber(fee_number).getHouse_number(), "waiting");
                break;
            case "rent":
            case "water":
            case "power":
            case "utilities":
            case "maintenance":
                break;
            default:
                throw new RuntimeException("费用类型错误");
        }
    }

    @Override
    public void updateFeeStatusByFeeNumber(String fee_number, boolean status) {
        feeMapper.updateFeeStatusByFeeNumber(fee_number, status);
    }

    @Override
    public List<Fee> getFeeByHouseNumber(String house_number) {
        return feeMapper.getFeeByHouseNumber(house_number);
    }

    @Override
    public List<Fee> getFeeByUserId(int user_id) {
        return feeMapper.getFeeByUserId(user_id);
    }

    @Override
    public List<Fee> getAllFee() {
        if(userServiceImpl.getUserInfo().getRole() == 1)
            throw new RuntimeException("权限不足");
        return feeMapper.getAllFee();
    }

    @Override
    public Fee getFeeById(int id) {
        return feeMapper.getFeeById(id);
    }

    @Override
    public Fee getFeeByFeeNumber(String fee_number) {
        return feeMapper.getFeeByFeeNumber(fee_number);
    }

    @Override
    public String createAliPayOrderForm(Fee fee) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();

        request.setNotifyUrl(alipayProperties.getNotifyUrl());
        request.setReturnUrl(alipayProperties.getReturnUrl());

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        request.setBizModel(model);
        model.setOutTradeNo(fee.getFee_number());
        model.setTotalAmount(fee.getAmount().toString());

        switch (fee.getType()) {
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
        model.setQrPayMode("2");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");

        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);

        if (response.isSuccess()) {
            return response.getBody();
        } else {
            throw new RuntimeException("支付宝支付页面生成失败");
        }
    }

    @Override
    public Fee getUserIdByFeeNumber(String fee_number) {
        return feeMapper.getUserIdByFeeNumber(fee_number);
    }

    @Override
    public Fee getTypeByFeeNumber(String fee_number) {
        return feeMapper.getTypeByFeeNumber(fee_number);
    }

    @Override
    public Fee getHouseNumberByFeeNumber(String fee_number) {
        return feeMapper.getHouseNumberByFeeNumber(fee_number);
    }

    @Override
    public Page<Fee> getFeeListWithPagination(String type, Integer paid, Integer status, int page, int size) {
        if(userServiceImpl.getUserInfo().getRole() == 1)
            throw new RuntimeException("权限不足");

        Page<Fee> feePage = new Page<>(page, size);
        // 使用 MyBatis Plus 提供的 QueryWrapper 进行条件查询
        QueryWrapper<Fee> queryWrapper = new QueryWrapper<>();
        if (type != null) {
            queryWrapper.eq("type", type);
        }
        if (paid != null) {
            queryWrapper.eq("paid", paid);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }

        return feeMapper.selectPage(feePage, queryWrapper);
    }
}
