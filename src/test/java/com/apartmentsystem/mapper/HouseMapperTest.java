package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.House;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
public class HouseMapperTest {
    @Autowired
    private HouseMapper houseMapper;

    @Test
    public void testinsertHouse() {
        House house = new House();
        house.setHouse_number("304");
        house.setBuilding_name("体育楼");
        house.setFloor(1);
        house.setStatus("vacant");
        houseMapper.insertHouse(house.getHouse_number(), house.getBuilding_name(), house.getFloor(), null, house.getStatus());
    }

    @Test
    public void testgetHouseByHouseNumber() {
        House house = houseMapper.getHouseByHouseNumber("304");
        System.out.println(house);
    }

    @Test
    public void testgetHouseById() {
        House house = houseMapper.getHouseById(7);
        System.out.println(house);
    }

    @Test
    public void testupdateOwnerByHouseNumber() {
        houseMapper.updateOwnerByHouseNumber("304", 1);
    }

    @Test
    public void testupdateStatusByHouseNumber() {
        houseMapper.updateStatusByHouseNumber("304", "occupied");
    }

    @Test
    public void testdeleteHouseByHouseNumber() {
        houseMapper.deleteHouseByHouseNumber("304");
    }
}
