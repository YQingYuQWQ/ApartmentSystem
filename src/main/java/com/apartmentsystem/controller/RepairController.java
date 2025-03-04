package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Repair;
import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.RepairServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repair")
@CrossOrigin
public class RepairController {
    @Autowired
    private RepairServiceImpl repairServiceImpl;

    @PostMapping("/insertRepair")
    public Result insertReppair(@RequestBody Repair repair) {
        repairServiceImpl.insertRepair(repair);
        return Result.success();
    }

    @PostMapping("/updateRepairStatusById")
    public Result updateRepairStatusById(@RequestBody Repair repair) {
        repairServiceImpl.updateRepairStatusById(repair);
        return Result.success();
    }

    @GetMapping("/selectRepairByHouseId")
    public Result selectRepairByHouseId(@RequestParam int house_id) {
        return Result.success(repairServiceImpl.selectRepairByHouseId(house_id));
    }

    @GetMapping("/getAllRepairs")
    public Result getAllRepairs() {
        return Result.success(repairServiceImpl.getAllRepairs());
    }

}
