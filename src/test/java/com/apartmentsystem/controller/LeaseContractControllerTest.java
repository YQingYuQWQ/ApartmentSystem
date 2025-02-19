package com.apartmentsystem.controller;

import com.apartmentsystem.entity.LeaseContract;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LeaseContractControllerTest {
    @Autowired
    private LeaseContractController leaseContractController;

    @Test
    public void testGetActiveLeaseContractByUserId() {
        LeaseContract leaseContract = new LeaseContract();
        leaseContract.setUser_id(1);
        System.out.println(leaseContractController.getActiveLeaseContractByUserId(leaseContract));
    }
}
