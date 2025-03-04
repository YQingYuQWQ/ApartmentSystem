package com.apartmentsystem.mapper;

import com.apartmentsystem.entity.Repair;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepairMapper extends BaseMapper<Repair> {
    void insertRepair(Repair repair);
    void updateRepairStatusById(Repair repair);
    List<Repair> selectRepairByHouseId(int house_id);
    List<Repair> getAllRepairs();
}
