package com.apartmentsystem.service;

import com.apartmentsystem.entity.User;
import com.apartmentsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    public void testInsertUser() {
    }

    @Test
    public void testInsertNormalUser() {
        User user = new User();
        user.setUsername("yqy");
        user.setPassword("123456");
        user.setEmail("28022748500@qq.com");
        user.setPhone("18242671270");
        user.setNick_name("danta");
        userServiceImpl.insertNormalUser(user);
    }
}
