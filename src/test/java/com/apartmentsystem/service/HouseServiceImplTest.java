package com.apartmentsystem.service;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.HouseServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HouseServiceImplTest {
    @Autowired
    private HouseServiceImpl houseServiceImpl;

    @Test
    public void testInsertHouse() {
        Result result = houseServiceImpl.insertHouse("304", "食堂", 1, 1, "occupied");
        System.out.println(result);
    }

    @Test
    public void testUpdateOwnerByHouseNumber() {
        Result result = houseServiceImpl.updateOwnerByHouseNumber("304", 4);
        System.out.println(result);
    }

    @Test
    public void testUpdateStatusByHouseNumber() {
        Result result = houseServiceImpl.updateStatusByHouseNumber("304", "vacant");
        System.out.println(result);
    }

    @Test
    public void testDeleteHouseByHouseNumber() {
        Result result = houseServiceImpl.deleteHouseByHouseNumber("304");
        System.out.println(result);
    }
}
