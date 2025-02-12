package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.House;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HouseMapper {
    void insertHouse(@Param("house_number") String house_number,
                     @Param("building_name") String building_name,
                     @Param("floor") int floor,
                     @Param("owner_id") Integer owner_id,
                     @Param("status") String status);
    House getHouseByHouseNumber(@Param("house_number") String house_number);
    House getHouseById(@Param("id") int id);
    void updateOwnerByHouseNumber(@Param("house_number") String house_number, @Param("owner_id") int owner_id);
    void updateStatusByHouseNumber(@Param("house_number") String house_number, @Param("status") String status);
    void deleteHouseByHouseNumber(@Param("house_number") String house_number);
    void updateWaterFeeByHouseNumber(@Param("house_number") String house_number, @Param("water_fee") String water_fee);
    void updatePowerFeeByHouseNumber(@Param("house_number") String house_number, @Param("power_fee") String power_fee);
}
