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
    public Result insertLeaseContract(LeaseContract leaseContract) {
        int house_id = leaseContract.getHouse_id();
        if(!houseServiceImpl.getHouseById(house_id).getStatus().equals("vacant"))
            return Result.error("房屋已被租赁");
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "创建合同" + leaseContract.getUser_id());
        leaseContractMapper.insertLeaseContract(leaseContract);
        houseServiceImpl.updateStatusByHouseId(leaseContract.getHouse_id(), "occupied");
        return Result.success("创建合同成功");
    }

    @Override
    public Result updateLeaseContract(LeaseContract leaseContract) {
        return null;
    }

    @Override
    public Result deleteLeaseContractById(LeaseContract leaseContract) {
        return null;
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
