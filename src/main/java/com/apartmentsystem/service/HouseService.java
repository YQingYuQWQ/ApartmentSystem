package com.apartmentsystem.service;


import com.apartmentsystem.entity.House;

import java.math.BigDecimal;
import java.util.List;

public interface HouseService {
    void insertHouse(House house);
    void updateOwnerByHouseNumber(String house_number, int owner_id);
    void updateStatusByHouseNumber(String house_number, String status);
    void updateStatusByHouseId(int id, String status);
    void deleteHouseByHouseNumber(String house_number);
    void updateWaterFeeByHouseNumber(String house_number, BigDecimal water_fee);
    void updatePowerFeeByHouseNumber(String house_number, BigDecimal power_fee);
    House getHouseByHouseNumber(String house_number);
    House getHouseById(int id);
    House getHouseByOwnerId(int owner_id);
    String getStatusByHouseNumber(String house_number);
    List<House> showHouseList();
    List<House> getHouseList();
}
