package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.House;
import com.apartmentsystem.entity.Result;
import com.apartmentsystem.mapper.HouseMapper;
import com.apartmentsystem.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseMapper houseMapper;

    @Override
    public Result insertHouse(String house_number, String building_name, int floor, int owner_id, String status) {
        House house = houseMapper.getHouseByHouseNumber(house_number);
        if(house!=null)
            return Result.error("房屋已存在");
        if(house_number==null||building_name==null||status==null)
            return Result.error("输入参数有误");
        houseMapper.insertHouse(house_number, building_name, floor, owner_id, status);
        return Result.success();
    }

    @Override
    public Result updateOwnerByHouseNumber(String house_number, int owner_id) {
        House house = houseMapper.getHouseByHouseNumber(house_number);
        if(house==null)
            return Result.error("房屋不存在");
        houseMapper.updateOwnerByHouseNumber(house_number, owner_id);
        return Result.success();
    }

    @Override
    public Result updateStatusByHouseNumber(String house_number, String status) {
        House house = houseMapper.getHouseByHouseNumber(house_number);
        if(house==null)
            return Result.error("房屋不存在");
        if(status != "occupied" && status != "vacant" && status != "under_maintenance")
            return Result.error("房屋状态有误");
        houseMapper.updateStatusByHouseNumber(house_number, status);
        return Result.success();
    }

    @Override
    public Result deleteHouseByHouseNumber(String house_number) {
        House house = houseMapper.getHouseByHouseNumber(house_number);
        if(house==null)
            return Result.error("房屋不存在");
        houseMapper.deleteHouseByHouseNumber(house_number);
        return Result.success();
    }

    @Override
    public House getHouseByHouseNumber(String house_number) {
        return houseMapper.getHouseByHouseNumber(house_number);
    }

    @Override
    public House getHouseById(int id) {
        return houseMapper.getHouseById(id);
    }
}
