package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.LeaseContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LeaseContractMapper {
    void insertLeaseContract(LeaseContract leaseContract);
    void updateLeaseContractByUserId(LeaseContract leaseContract);
    void updateStatusById(LeaseContract leaseContract);
    void deleteLeaseContractById(LeaseContract leaseContract);
    LeaseContract selectLeaseContractByUserName(String user_name);
    List<LeaseContract> selectAllLeaseContract();
    LeaseContract selectActiveLeaseContractByUserId(@Param("user_id") int user_id);
}
