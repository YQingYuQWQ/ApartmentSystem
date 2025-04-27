package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.HouseMonthlyBillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/houseMonthlyBill")
public class HouseMonthlyBillController {
    @Autowired
    private HouseMonthlyBillServiceImpl houseMonthlyBillServiceImpl;

    @PostMapping("/getPayingHouseMonthlyRentBillByHouseId")
    public Result getPayingHouseMonthlyRentBillByHouseId(@RequestParam int house_id) {
        return Result.success(houseMonthlyBillServiceImpl.getPayingHouseMonthlyRentBillByHouseId(house_id));
    }
}
