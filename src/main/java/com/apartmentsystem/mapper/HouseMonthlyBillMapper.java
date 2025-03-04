package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.HouseMonthlyBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HouseMonthlyBillMapper extends BaseMapper<HouseMonthlyBill> {
    void insertHouseMonthlyBill(HouseMonthlyBill houseMonthlyBill);
}
