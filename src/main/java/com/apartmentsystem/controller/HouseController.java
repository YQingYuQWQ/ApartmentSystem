package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.HouseServiceImpl;
import com.apartmentsystem.service.impl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseServiceImpl houseServiceImpl;
    @Autowired
    private LogServiceImpl logServiceImpl;

    @PostMapping("/insert")
    public Result insertHouse(@RequestParam String house_number,
                              @RequestParam String building_name,
                              @RequestParam int floor,
                              @RequestParam(defaultValue = "null") Integer owner_id,
                              @RequestParam String status) {
        return houseServiceImpl.insertHouse(house_number, building_name, floor, owner_id, status);
    }

    @PostMapping("/updateOwner")
    public Result updateOwnerByHouseNumber(@RequestParam String house_number,
                                           @RequestParam int owner_id) {
        return houseServiceImpl.updateOwnerByHouseNumber(house_number, owner_id);
    }

    @PostMapping("/updateStatus")
    public Result updateStatusByHouseNumber(@RequestParam String house_number,
                                            @RequestParam String status) {
        return houseServiceImpl.updateStatusByHouseNumber(house_number, status);
    }

    @PostMapping("/delete")
    public Result deleteHouseByHouseNumber(@RequestParam String house_number) {
        return houseServiceImpl.deleteHouseByHouseNumber(house_number);
    }

    @GetMapping("/getByHouseNumber")
    public Result getHouseByHouseNumber(@RequestParam String house_number) {
        return Result.success(houseServiceImpl.getHouseByHouseNumber(house_number));
    }

    @GetMapping("/getById")
    public Result getHouseById(@RequestParam int id) {
        return Result.success(houseServiceImpl.getHouseById(id));
    }
}
