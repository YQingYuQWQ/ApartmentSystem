package com.apartmentsystem.service;

import com.apartmentsystem.entity.HouseMonthlyBill;

import java.util.List;

public interface HouseMonthlyBillService {
    void insertHouseMonthBill(HouseMonthlyBill houseMonthlyBill);
    void updateRent_statusById(int id, int rent_status);
    List<HouseMonthlyBill> getPayingHouseMonthlyRentBillByHouseId(int house_id);
}
