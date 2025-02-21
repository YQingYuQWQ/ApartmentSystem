package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.LeaseContract;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@SpringBootTest
public class LeaseContractMapperTest {
    @Autowired
    private LeaseContractMapper leaseContractMapper;

    @Test
    public void testInsertLeaseContract() {
        LeaseContract leaseContract = new LeaseContract();
        leaseContract.setHouse_id(1);
        leaseContract.setUser_id(1);
        leaseContract.setStart_date(Date.valueOf("2025-01-01"));
        leaseContract.setEnd_date(Date.valueOf("2025-07-01"));
        leaseContract.setContract_status("active");
        leaseContract.setMonthly_rent(BigDecimal.valueOf(1000));
        leaseContract.setDeposit(BigDecimal.valueOf(1000));
        leaseContract.setCreated_at(new Timestamp(System.currentTimeMillis()));
        leaseContract.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        leaseContractMapper.insertLeaseContract(leaseContract);
    }

    @Test
    public void testUpdateLeaseContractByUserId() {
        LeaseContract leaseContract = new LeaseContract();
        leaseContract.setHouse_id(1);
        leaseContract.setUser_id(1);
        leaseContract.setStart_date(Date.valueOf("2025-01-01"));
        leaseContract.setEnd_date(Date.valueOf("2025-07-01"));
        leaseContract.setContract_status("active");
        leaseContract.setMonthly_rent(BigDecimal.valueOf(1000));
        leaseContract.setDeposit(BigDecimal.valueOf(1000));
        leaseContract.setCreated_at(new Timestamp(System.currentTimeMillis()));
        leaseContract.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        leaseContractMapper.updateLeaseContractByUserId(leaseContract);
    }

    @Test
    public void testDeleteLeaseContractById() {
        LeaseContract leaseContract = new LeaseContract();
        leaseContract.setId(1);
        leaseContractMapper.deleteLeaseContractById(leaseContract);
    }

    @Test
    public void testSelectLeaseContractByUserName() {
        LeaseContract leaseContract = new LeaseContract();
        leaseContract.setUser_id(1);
        leaseContractMapper.selectLeaseContractByUserName(leaseContract);
    }

    @Test
    public void testSelectAllLeaseContract() {
        leaseContractMapper.selectAllLeaseContract();
    }

    @Test
    public void testSelectActiveLeaseContractByUserId() {
    }

    @Autowired
    private HouseMapper houseMapper;

    @Test
    public void testGetStatusByHouseNumber(){
        if(houseMapper.getStatusByHouseNumber("99").equals("vacant"))
            System.out.println("房屋未被租赁");
        else
            System.out.println("房屋已被租赁");
    }
}
