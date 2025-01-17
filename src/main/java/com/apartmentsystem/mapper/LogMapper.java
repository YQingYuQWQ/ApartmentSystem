package com.apartmentsystem.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {
    void insertLog(int user_id, String action);
}
