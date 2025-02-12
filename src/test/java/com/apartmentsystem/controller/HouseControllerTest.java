package com.apartmentsystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HouseControllerTest {
    @Autowired
    private HouseController houseController;

    @Test
    public void testInsertHouse() {
        houseController.insertHouse("304", "体育楼", 3, null, "vacant");
    }
}
