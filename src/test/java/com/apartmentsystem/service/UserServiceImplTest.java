package com.apartmentsystem.service;

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
        System.out.println(userServiceImpl.insertUser("test", "123456", "2802274853@qq.com", "18242671272", 0));;
    }
}
