package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    //根据用户名获取用户
    User getUserByUserName(@Param("username") String username);
    User getUserByEmail(@Param("email") String email);
    User getUserByPhone(@Param("phone") String phone);
    User getUserAllByUserName(@Param("username") String username);
    //根据id更新用户密码
    Boolean updateUserPasswordById(@Param("id") int id, @Param("password") String password);
    Boolean updateUserAvatarById(User user);
    Boolean insertUser(@Param("username") String username, @Param("password") String password, @Param("email") String email, @Param("phone") String phone, @Param("role") int role);
    Boolean insertNormalUser(User user);
    List<User> getAllUser();
    void updateUserById(User user);
    void createUser(User user);
    void deleteUserById(int id);
}
