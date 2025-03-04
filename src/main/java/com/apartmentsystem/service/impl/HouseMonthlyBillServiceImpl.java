package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.HouseMonthlyBill;
import com.apartmentsystem.mapper.HouseMonthlyBillMapper;
import com.apartmentsystem.service.HouseMonthlyBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseMonthlyBillServiceImpl implements HouseMonthlyBillService {
    @Autowired
    private HouseMonthlyBillMapper houseMonthlyBillMapper;

    @Override
    public void insertHouseMonthBill(HouseMonthlyBill houseMonthlyBill) {
        houseMonthlyBillMapper.insertHouseMonthlyBill(houseMonthlyBill);
    }
}
