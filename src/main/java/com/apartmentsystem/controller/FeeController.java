package com.apartmentsystem.controller;

import com.alipay.api.AlipayApiException;
import com.apartmentsystem.entity.Fee;
import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.FeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/fee")
public class FeeController {
    @Autowired
    private FeeServiceImpl feeServiceImpl;

    @PostMapping("/insertFee")
    public Result insertFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertFee(fee).getData());
    }

    @PostMapping("/updateFee")
    public Result updateFeeByHouseNumber(@RequestParam int id) {
        return Result.success(feeServiceImpl.updateFeePaidById(id).getData());
    }

    @GetMapping("/getFeeByHouseNumber")
    public Result getFeeByHouseNumber(@RequestParam String house_number) {
        return Result.success(feeServiceImpl.getFeeByHouseNumber(house_number));
    }

    @GetMapping("/getFeeById")
    public void getFeeById(@RequestParam int id) {
        feeServiceImpl.getFeeById(id);
    }
}
