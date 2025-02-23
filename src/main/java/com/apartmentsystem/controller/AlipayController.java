package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.AlipayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@RestController
@CrossOrigin
@RequestMapping("/alipay")
public class AlipayController {

    @Autowired
    private AlipayServiceImpl alipayServiceImpl;

    @PostMapping("/notify")
    public Result alipayNotify(HttpServletRequest request) {
        System.out.println("支付宝支付成功回调");
        alipayServiceImpl.handleAlipayCallback(request);
        return Result.success();
    }
}
