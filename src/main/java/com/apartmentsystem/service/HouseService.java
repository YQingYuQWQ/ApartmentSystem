package com.apartmentsystem.service;


import com.apartmentsystem.entity.House;
import com.apartmentsystem.entity.Result;

import java.util.List;

public interface HouseService {
    Result insertHouse(House house);
    Result updateOwnerByHouseNumber(String house_number, int owner_id);
    Result updateStatusByHouseNumber(String house_number, String status);
    Result updateStatusByHouseId(int id, String status);
    Result deleteHouseByHouseNumber(String house_number);
    House getHouseByHouseNumber(String house_number);
    House getHouseById(int id);
    Result showHouseList();
}
