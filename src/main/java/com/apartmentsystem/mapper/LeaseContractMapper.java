package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.LeaseContract;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LeaseContractMapper {
    void insertLeaseContract(LeaseContract leaseContract);
    void updateLeaseContractByUserId(LeaseContract leaseContract);
    void deleteLeaseContractById(LeaseContract leaseContract);
    LeaseContract selectLeaseContractByUserName(LeaseContract leaseContract);
    List<LeaseContract> selectAllLeaseContract();
    LeaseContract selectActiveLeaseContractByUserId(LeaseContract leaseContract);
}
