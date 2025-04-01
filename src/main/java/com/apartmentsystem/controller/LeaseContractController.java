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


    /**
     * Get active lease contract by user id
     * @param leaseContract lease contract
     * @return Result
     */
    @PostMapping("/getActiveLeaseContractByUserId")
    public Result getActiveLeaseContractByUserId(@RequestBody LeaseContract leaseContract) {
        return Result.success(leaseContractServiceImpl.selectActiveLeaseContractByUserId(leaseContract.getUser_id()));
    }

    /**
     * insert lease contract
     * @param leaseContract lease contract
     * @return Result
     */
    @PostMapping("/insertLeaseContract")
    public Result insertLeaseContract(@RequestBody LeaseContract leaseContract) {
        return Result.success(leaseContractServiceImpl.insertLeaseContract(leaseContract));
    }

    /**
     * Get all lease contract
     * @return Result
     */
    @PostMapping("/getAllLeaseContract")
    public Result getAllLeaseContract() {
        return Result.success(leaseContractServiceImpl.selectAllLeaseContract());
    }
}
