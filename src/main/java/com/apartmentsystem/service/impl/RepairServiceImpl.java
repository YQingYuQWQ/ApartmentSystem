package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.Repair;
import com.apartmentsystem.mapper.RepairMapper;
import com.apartmentsystem.service.RepairService;
import com.apartmentsystem.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairMapper repairMapper;
    @Autowired
    private LogServiceImpl logServiceImpl;

    @Override
    public void insertRepair(Repair repair) {
        repairMapper.insertRepair(repair);
        logServiceImpl.insertLog(UserHolder.getUser().getId(), "提交维修申请");
    }

    @Override
    public void updateRepairStatusById(Repair repair) {
        repairMapper.updateRepairStatusById(repair);
    }

    @Override
    public List<Repair> selectRepairByHouseId(int house_id) {
        return repairMapper.selectRepairByHouseId(house_id);
    }

    @Override
    public List<Repair> getAllRepairs() {
        return repairMapper.getAllRepairs();
    }
}
