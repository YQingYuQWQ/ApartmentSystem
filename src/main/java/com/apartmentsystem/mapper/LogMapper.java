package com.apartmentsystem.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LogMapper {
    void insertLog(@Param("user_id") int user_id, @Param("action") String action);
}
