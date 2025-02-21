package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.LeaseContract;
import com.apartmentsystem.entity.Result;
import com.apartmentsystem.mapper.LeaseContractMapper;
import com.apartmentsystem.service.LeaseContractService;
import com.apartmentsystem.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaseContractServiceImpl implements LeaseContractService {
    @Autowired
    private LeaseContractMapper leaseContractMapper;
    @Autowired
    private HouseServiceImpl houseServiceImpl;
    @Autowired
    private LogServiceImpl logServiceImpl;


    @Override
    public Integer insertLeaseContract(LeaseContract leaseContract) {
        if(!"waiting".equals(houseServiceImpl.getHouseById(leaseContract.getHouse_id()).getStatus()))
            throw new RuntimeException("房屋不处于已预定状态");

        leaseContract.setDeposit(houseServiceImpl.getHouseById(leaseContract.getHouse_id()).getDeposit());
        leaseContract.setMonthly_rent(houseServiceImpl.getHouseById(leaseContract.getHouse_id()).getPrice());

        logServiceImpl.insertLog(UserHolder.getUser().getId(), "创建合同" + leaseContract.getUser_id());
        leaseContractMapper.insertLeaseContract(leaseContract);
        houseServiceImpl.updateStatusByHouseId(leaseContract.getHouse_id(), "occupied");
        return leaseContract.getId();
    }

    @Override
    public void updateLeaseContract(LeaseContract leaseContract) {
        leaseContractMapper.updateLeaseContractByUserId(leaseContract);
    }

    @Override
    public LeaseContract selectLeaseContractByUserName(LeaseContract leaseContract) {
        return null;
    }

    @Override
    public List<LeaseContract> selectAllLeaseContract() {
        return leaseContractMapper.selectAllLeaseContract();
    }

    @Override
    public LeaseContract selectActiveLeaseContractByUserId(int user_id) {
        return leaseContractMapper.selectActiveLeaseContractByUserId(user_id);
    }
}
