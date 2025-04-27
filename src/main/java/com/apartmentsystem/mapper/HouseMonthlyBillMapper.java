package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.HouseMonthlyBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HouseMonthlyBillMapper extends BaseMapper<HouseMonthlyBill> {
    void insertHouseMonthlyBill(HouseMonthlyBill houseMonthlyBill);
    void updateRent_statusById(@Param("id") int id, @Param("rent_status")int rent_status);
    List<HouseMonthlyBill> getPayingHouseMonthlyRentBillByHouseId(int house_id);
}
