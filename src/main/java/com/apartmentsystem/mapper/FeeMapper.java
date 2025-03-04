package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.Fee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FeeMapper extends BaseMapper<Fee> {
    void insertFee(Fee fee);
    void updateFeePaidById(@Param("id")int id);
    void updateFeePaidByFeeNumber(@Param("fee_number")String fee_number, @Param("status")boolean status);
    void updateFeeStatusByFeeNumber(@Param("fee_number")String fee_number, @Param("status")boolean status);
    List<Fee> getFeeByHouseNumber(@Param("house_number")String house_number);
    List<Fee> getFeeByStatus(@Param("status")boolean status);
    List<Fee> getFeeByUserId(@Param("user_id")int user_id);
    List<Fee> getAllFee();
    Fee getFeeById(@Param("id")int id);
    Fee getFeeByFeeNumber(@Param("fee_number")String fee_number);
    Fee getFalsePaidFeeByHouseNumber(@Param("house_number")String house_number);
    Fee getUserIdByFeeNumber(@Param("fee_number")String fee_number);
    Fee getTypeByFeeNumber(@Param("fee_number")String fee_number);
    Fee getHouseNumberByFeeNumber(@Param("fee_number")String fee_number);
}
