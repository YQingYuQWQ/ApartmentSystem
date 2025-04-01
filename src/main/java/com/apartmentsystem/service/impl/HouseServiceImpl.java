package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.House;
import com.apartmentsystem.mapper.HouseMapper;
import com.apartmentsystem.service.HouseService;
import com.apartmentsystem.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    private HouseMapper houseMapper;
    @Autowired
    private LogServiceImpl logServiceImpl;

    @Override
    public void insertHouse(House house) {
        House house1 = houseMapper.getHouseByHouseNumber(house.getHouse_number());
        if(house1!=null)
            throw new RuntimeException("房屋已存在");
        if(house.getHouse_number()==null||house.getBuilding_name()==null||house.getStatus()==null)
            throw new RuntimeException("房屋信息不完整");
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "insert house " + "house_number: " +house.getHouse_number()+ " building_name: "+house.getBuilding_name()+" floor: "+house.getFloor());
        houseMapper.insertHouse(house);
    }

    @Override
    public void updateOwnerByHouseNumber(String house_number, int owner_id) {
        House house = houseMapper.getHouseByHouseNumber(house_number);
        if(house==null)
            throw new RuntimeException("房屋不存在");
        //logServiceImpl.insertLog(UserHolder.getUser().getId(), "update house owner" + "house_number: "+house_number+" owner_id: "+owner_id);
        houseMapper.updateOwnerByHouseNumber(house_number, owner_id);
    }

    @Override
    public void updateStatusByHouseNumber(String house_number, String status) {
        House house = houseMapper.getHouseByHouseNumber(house_number);
        if(house==null)
            throw new RuntimeException("房屋不存在");
//        if(!Objects.equals(status, "occupied") && !Objects.equals(status, "vacant") && !Objects.equals(status, "under_maintenance") && !Objects.equals(status, "booked"))
//            throw new RuntimeException("房屋状态有误");
//        logServiceImpl.insertLog(UserHolder.getUser().getId(), "update house status" + "house_number: "+house_number+" status: "+status);
        houseMapper.updateStatusByHouseNumber(house_number, status);
    }

    @Override
    public void updateStatusByHouseId(int id, String status) {
        if(!Objects.equals(status, "occupied") && !Objects.equals(status, "vacant") && !Objects.equals(status, "under_maintenance"))
            throw new RuntimeException("房屋状态有误");
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "update house status" + "id: "+id+" status: "+status);
        houseMapper.updateStatusByHouseId(id, status);
    }

    @Override
    public void deleteHouseByHouseNumber(String house_number) {
        House house = houseMapper.getHouseByHouseNumber(house_number);
        if(house==null)
            throw new RuntimeException("房屋不存在");
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "delete house" + "house_number: "+house_number);
        houseMapper.deleteHouseByHouseNumber(house_number);
    }

    @Override
    public void updateWaterFeeByHouseNumber(String house_number, BigDecimal water_fee) {
        House house = houseMapper.getHouseByHouseNumber(house_number);
        if(house==null)
            throw new RuntimeException("房屋不存在");
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "update house water fee" + "house_number: "+house_number+" water_fee: "+water_fee);
        houseMapper.updateWaterFeeByHouseNumber(house_number, water_fee);
    }

    @Override
    public void updatePowerFeeByHouseNumber(String house_number, BigDecimal power_fee) {
        House house = houseMapper.getHouseByHouseNumber(house_number);
        if(house==null)
            throw new RuntimeException("房屋不存在");
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "update house power fee" + "house_number: "+house_number+" power_fee: "+power_fee);
        houseMapper.updatePowerFeeByHouseNumber(house_number, power_fee);
    }

    @Override
    public void updateHouseInfoByHouseNumber(House house) {
        if(1 == UserHolder.getUser().getRole())
            throw new RuntimeException("权限不足");
        House house1 = houseMapper.getHouseByHouseNumber(house.getHouse_number());
        if(null == house1)
            throw new RuntimeException("房屋不存在");
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "update house info" + "house_number: "+house.getHouse_number());
        houseMapper.updateHouseInfoByHouseNumber(house);
    }

    @Override
    public House getHouseByHouseNumber(String house_number) {
        return houseMapper.getHouseByHouseNumber(house_number);
    }

    @Override
    public House getHouseById(int id) {
        return houseMapper.getHouseById(id);
    }

    @Override
    public House getHouseByOwnerId(int owner_id) {
        return houseMapper.getHouseByOwnerId(owner_id);
    }

    @Override
    public String getStatusByHouseNumber(String house_number) {
        return houseMapper.getStatusByHouseNumber(house_number);
    }

    @Override
    public List<House> showHouseList() {
        return houseMapper.showHouseList();
    }

    @Override
    public List<House> getHouseList() {
        if (1 == UserHolder.getUser().getRole())
            throw new RuntimeException("权限不足");
        return houseMapper.getHouseList();
    }
}
