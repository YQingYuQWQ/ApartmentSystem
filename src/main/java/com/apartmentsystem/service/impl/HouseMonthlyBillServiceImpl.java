package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.HouseMonthlyBill;
import com.apartmentsystem.mapper.HouseMonthlyBillMapper;
import com.apartmentsystem.service.HouseMonthlyBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseMonthlyBillServiceImpl implements HouseMonthlyBillService {
    @Autowired
    private HouseMonthlyBillMapper houseMonthlyBillMapper;

    @Override
    public void insertHouseMonthBill(HouseMonthlyBill houseMonthlyBill) {
        houseMonthlyBillMapper.insertHouseMonthlyBill(houseMonthlyBill);
    }

    @Override
    public void updateRent_statusById(int id, int rent_status) {
        houseMonthlyBillMapper.updateRent_statusById(id, rent_status);
    }

    // 获取状态为未缴纳的房屋租金账单
    @Override
    public List<HouseMonthlyBill> getPayingHouseMonthlyRentBillByHouseId(int id) {
        return houseMonthlyBillMapper.getPayingHouseMonthlyRentBillByHouseId(id);
    }
}
