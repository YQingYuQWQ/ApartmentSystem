package com.apartmentsystem.controller;

import com.apartmentsystem.entity.LeaseContract;
import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.LeaseContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leaseContract")
@CrossOrigin
public class LeaseContractController {
    @Autowired
    private LeaseContractServiceImpl leaseContractServiceImpl;

    @PostMapping("/getActiveLeaseContractByUserId")
    public Result getActiveLeaseContractByUserId(@RequestBody LeaseContract leaseContract) {
        return Result.success(leaseContractServiceImpl.selectActiveLeaseContractByUserId(leaseContract));
    }
}
