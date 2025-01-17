package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.HouseServiceImpl;
import com.apartmentsystem.service.impl.LogServiceImpl;
import com.apartmentsystem.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseServiceImpl houseServiceImpl;
    @Autowired
    private LogServiceImpl logServiceImpl;

    @PostMapping
    public Result insertHouse(@RequestParam String house_number,
                              @RequestParam String building_name,
                              @RequestParam int floor,
                              @RequestParam int owner_id,
                              @RequestParam String status,
                              HttpServletRequest request) {
        logServiceImpl.insertLog(RequestUtil.getUserIdFromRequest(request),"新增房屋:" + building_name);
        return houseServiceImpl.insertHouse(house_number, building_name, floor, owner_id, status);
    }

    @PutMapping("/updateOwner")
    public Result updateOwnerByHouseNumber(@RequestParam String house_number,
                                           @RequestParam int owner_id,
                                           HttpServletRequest request) {
        logServiceImpl.insertLog(RequestUtil.getUserIdFromRequest(request),"改变房屋租户:" + owner_id+ "房屋号:" + house_number);
        return houseServiceImpl.updateOwnerByHouseNumber(house_number, owner_id);
    }

    @PutMapping("/updateStatus")
    public Result updateStatusByHouseNumber(@RequestParam String house_number,
                                            @RequestParam String status,
                                            HttpServletRequest request) {
        logServiceImpl.insertLog(RequestUtil.getUserIdFromRequest(request),"改变房屋状态:" + status+ "房屋号:" + house_number);
        return houseServiceImpl.updateStatusByHouseNumber(house_number, status);
    }

    @PostMapping("/delete")
    public Result deleteHouseByHouseNumber(@RequestParam String house_number,HttpServletRequest request) {
        logServiceImpl.insertLog(RequestUtil.getUserIdFromRequest(request),"删除房屋:" + house_number);
        return houseServiceImpl.deleteHouseByHouseNumber(house_number);
    }

    @GetMapping("/getByHouseNumber")
    public Result getHouseByHouseNumber(@RequestParam String house_number, HttpServletRequest request) {
        logServiceImpl.insertLog(RequestUtil.getUserIdFromRequest(request),"查看房屋:" + house_number);
        return Result.success(houseServiceImpl.getHouseByHouseNumber(house_number));
    }

    @GetMapping("/getById")
    public Result getHouseById(@RequestParam int id) {
        return Result.success(houseServiceImpl.getHouseById(id));
    }
}
