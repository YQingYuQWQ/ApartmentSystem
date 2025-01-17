package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.House;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseMapper {
    void insertHouse(String house_number, String building_name, int floor, int owner_id, String status);
    House getHouseByHouseNumber(String house_number);
    House getHouseById(int id);
    void updateOwnerByHouseNumber(String house_number, int owner_id);
    void updateStatusByHouseNumber(String house_number, String status);
    void deleteHouseByHouseNumber(String house_number);
}
