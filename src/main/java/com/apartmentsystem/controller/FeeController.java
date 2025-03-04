package com.apartmentsystem.controller;

import com.alipay.api.AlipayApiException;
import com.apartmentsystem.entity.Fee;
import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.FeeServiceImpl;
import com.apartmentsystem.util.UserHolder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
        return Result.success(feeServiceImpl.insertFee(fee));
    }

    @PostMapping("/insertPowerFee")
    public Result insertPowerFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertPowerFee(fee));
    }

    @PostMapping("/insertWaterFee")
    public Result insertWaterFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertWaterFee(fee));
    }

    @PostMapping("/insertMaintenanceFee")
    public Result insertMaintenanceFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertMaintenanceFee(fee));
    }

    @PostMapping("/insertUtilitiesFee")
    public Result insertUtilitiesFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertUtilitiesFee(fee));
    }

    @PostMapping("/insertRentFee")
    public Result insertRentFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertRentFee(fee));
    }

    @GetMapping("/getFeeByHouseNumber")
    public Result getFeeByHouseNumber(@RequestParam String house_number) {
        return Result.success(feeServiceImpl.getFeeByHouseNumber(house_number));
    }

    @GetMapping("/getFeeById")
    public Result getFeeById(@RequestParam int id) {
        return Result.success(feeServiceImpl.getFeeById(id));
    }

    @PostMapping("/createDepositFee")
    public Result createDepositFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertDepositFee(fee));
    }

    @PostMapping("/createRentFee")
    public Result createRentFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertRentFee(fee));
    }

    @PostMapping("/createUtilitiesFee")
    public Result createUtilitiesFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertUtilitiesFee(fee));
    }

    @PostMapping("/createMaintenanceFee")
    public Result createMaintenanceFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertMaintenanceFee(fee));
    }

    @PostMapping("/createWaterFee")
    public Result createWaterFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertWaterFee(fee));
    }

    @PostMapping("/createPowerFee")
    public Result createPowerFee(@RequestBody Fee fee) throws AlipayApiException {
        return Result.success(feeServiceImpl.insertPowerFee(fee));
    }

    @PostMapping("/getByUserId")
    public Result getByUserId() {
        return Result.success(feeServiceImpl.getFeeByUserId(UserHolder.getUser().getId()));
    }

    // 分类查询接口，带分页
    @GetMapping("/list")
    public Result<Page<Fee>> getFeeList(@RequestParam(value = "type", required = false) String type,
                                @RequestParam(value = "paid", required = false) Integer paid,
                                @RequestParam(value = "status", required = false) Integer status,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size) {
        return Result.success(feeServiceImpl.getFeeListWithPagination(type, paid, status, page, size));
    }
}
