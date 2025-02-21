package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.Fee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeeMapper {
    void insertFee(Fee fee);
    void updateFeePaidById(@Param("id")int id);
    List<Fee> getFeeByHouseNumber(@Param("house_number")String house_number);
    Fee getFeeById(@Param("id")int id);
    Fee getFeeByFeeNumber(@Param("fee_number")String fee_number);
    List<Fee> getFeeByStatus(@Param("status")boolean status);
    Fee getFalsePaidFeeByHouseNumber(@Param("house_number")String house_number);
}
