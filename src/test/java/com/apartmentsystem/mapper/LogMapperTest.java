package com.apartmentsystem.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogMapperTest {
    @Autowired
    private LogMapper logMapper;

    @Test
    public void testInsertLog() {
        logMapper.insertLog(1, "test");
    }
}
