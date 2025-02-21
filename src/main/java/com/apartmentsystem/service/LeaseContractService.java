package com.apartmentsystem.service;

import com.apartmentsystem.entity.LeaseContract;
import com.apartmentsystem.entity.Result;

import java.util.List;

public interface LeaseContractService {
    Integer insertLeaseContract(LeaseContract leaseContract);
    void updateLeaseContract(LeaseContract leaseContract);
    LeaseContract selectLeaseContractByUserName(LeaseContract leaseContract);
    List<LeaseContract> selectAllLeaseContract();
    LeaseContract selectActiveLeaseContractByUserId(int user_id);
}
