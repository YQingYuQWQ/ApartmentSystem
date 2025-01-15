package com.apartmentsystem.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testInsertUser() {
        userMapper.insertUser("test", "123456", "2802274853@qq.com", "18242671272", 0);
    }

}
