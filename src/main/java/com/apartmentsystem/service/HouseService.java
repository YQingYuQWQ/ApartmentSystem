package com.apartmentsystem.service;


import com.apartmentsystem.entity.House;

import java.util.List;

public interface HouseService {
    void insertHouse(House house);
    void updateOwnerByHouseNumber(String house_number, int owner_id);
    void updateStatusByHouseNumber(String house_number, String status);
    void updateStatusByHouseId(int id, String status);
    void deleteHouseByHouseNumber(String house_number);
    House getHouseByHouseNumber(String house_number);
    House getHouseById(int id);
    String getStatusByHouseNumber(String house_number);
    List<House> showHouseList();
}
