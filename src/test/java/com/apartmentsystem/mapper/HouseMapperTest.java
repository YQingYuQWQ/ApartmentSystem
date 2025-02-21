package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.House;
import org.apache.ibatis.jdbc.Null;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

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
        house.setArea(18);
        house.setPrice(BigDecimal.valueOf(2400));
        house.setOwner_id(null);
        houseMapper.insertHouse(house);
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

    @Test
    public void testShowHouseList() {
        System.out.println(houseMapper.showHouseList());
    }

    @Test
    public void testGetHouseList() {
        System.out.println(houseMapper.getHouseList());
    }
}
