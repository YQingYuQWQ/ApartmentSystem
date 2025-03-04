package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setUsername("oio");
        user.setPassword("123456");
        user.setEmail("123009@qq.com");
        user.setPhone("12345678901");
        user.setNick_name("test1");
        System.out.println(userMapper.insertNormalUser(user));
    }

    @Test
    public void testGetUserByUserName() {
        System.out.println(userMapper.getUserByUserName("test1"));
    }

    @Test
    public void getUserInfo() {
        System.out.println(userMapper.getUserByUserName("yiqingyu"));
    }

    @Test
    public void testGetAllUser() {
        System.out.println(userMapper.getAllUser());
    }
}
