package com.apartmentsystem.service;

import com.apartmentsystem.entity.Repair;

import java.util.List;

public interface RepairService {
    void insertRepair(Repair repair);
    void updateRepairStatusById(Repair repair);
    List<Repair> selectRepairByHouseId(int house_id);
    List<Repair> getAllRepairs();
}
