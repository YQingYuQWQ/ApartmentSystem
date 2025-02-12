package com.apartmentsystem.service;


import com.apartmentsystem.entity.House;
import com.apartmentsystem.entity.Result;

public interface HouseService {
    Result insertHouse(String house_number, String building_name, int floor, Integer owner_id, String status);
    Result updateOwnerByHouseNumber(String house_number, int owner_id);
    Result updateStatusByHouseNumber(String house_number, String status);
    Result deleteHouseByHouseNumber(String house_number);
    House getHouseByHouseNumber(String house_number);
    House getHouseById(int id);
}
