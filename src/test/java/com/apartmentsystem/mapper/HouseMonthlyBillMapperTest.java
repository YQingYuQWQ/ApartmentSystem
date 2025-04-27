package com.apartmentsystem.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HouseMonthlyBillMapperTest {
    @Autowired
    private HouseMonthlyBillMapper houseMonthlyBillMapper;

    @Test
    public void testUpdateRent_statusById() {
        houseMonthlyBillMapper.updateRent_statusById(2, 1);
    }
}
