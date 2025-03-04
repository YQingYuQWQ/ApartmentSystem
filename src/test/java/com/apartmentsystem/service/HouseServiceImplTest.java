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

    }

    @Test
    public void testUpdateOwnerByHouseNumber() {
        }

    @Test
    public void testUpdateStatusByHouseNumber() {
    }

    @Test
    public void testDeleteHouseByHouseNumber() {
    }

    @Test
    public void testShowHouseList() {
    }

    @Test
    public void testGetHouseByOwnerId() {
        System.out.println(houseServiceImpl.getHouseByOwnerId(14));
    }
}
