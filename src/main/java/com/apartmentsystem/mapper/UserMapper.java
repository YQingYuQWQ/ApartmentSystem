package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    //根据用户名获取用户
    User getUserByUserName(String userName);
    User getUserByEmail(String email);
    User getUserByPhone(String phone);
    //根据id更新用户密码
    void updateUserPasswordById(@Param("id") int id, @Param("password") String password);
    void insertUser(@Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("phone") String phone, @Param("role") int role);
}
