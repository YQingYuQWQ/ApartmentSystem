package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.HouseMonthlyBill;
import com.apartmentsystem.entity.LeaseContract;
import com.apartmentsystem.mapper.LeaseContractMapper;
import com.apartmentsystem.service.LeaseContractService;
import com.apartmentsystem.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaseContractServiceImpl implements LeaseContractService {
    @Autowired
    private LeaseContractMapper leaseContractMapper;
    @Autowired
    private HouseServiceImpl houseServiceImpl;
    @Autowired
    private LogServiceImpl logServiceImpl;
    @Autowired
    private HouseMonthlyBillServiceImpl houseMonthBillServiceImpl;


    @Override
    public Integer insertLeaseContract(LeaseContract leaseContract) {
        if(1 == UserHolder.getUser().getRole())
            throw new RuntimeException("权限不足");
        if(!"waiting".equals(houseServiceImpl.getHouseById(houseServiceImpl.getHouseByHouseNumber(leaseContract.getHouse_number()).getId()).getStatus()))
            throw new RuntimeException("房屋不处于待入住状态");

        leaseContract.setDeposit(houseServiceImpl.getHouseById(leaseContract.getHouse_id()).getDeposit());
        leaseContract.setMonthly_rent(houseServiceImpl.getHouseById(leaseContract.getHouse_id()).getPrice());

        logServiceImpl.insertLog(UserHolder.getUser().getId(), "创建合同" + leaseContract.getUser_id());
        leaseContractMapper.insertLeaseContract(leaseContract);
        houseServiceImpl.updateStatusByHouseId(leaseContract.getHouse_id(), "occupied");


        // 生成账单
        HouseMonthlyBill houseMonthlyBill = new HouseMonthlyBill();
        houseMonthlyBill.setHouse_id(leaseContract.getHouse_id());
        houseMonthlyBill.setHouse_number(leaseContract.getHouse_number());
        houseMonthlyBill.setMonthly_rent(leaseContract.getMonthly_rent());
        houseMonthlyBill.setRent_status(false);
        houseMonthlyBill.setMonthly_property_fee(BigDecimal.valueOf(0));
        houseMonthlyBill.setProperty_fee_status(false);

        LocalDate startDate = leaseContract.getStart_date().toLocalDate();
        LocalDate endDate = leaseContract.getEnd_date().toLocalDate();

        for(int i = 0; i < leaseContractMapper.selectActiveLeaseContractByUserId(leaseContract.getUser_id()).getCount_month(); i++) {
            LocalDate billStart = startDate.plusMonths(i);
            LocalDate billEnd = startDate.plusMonths(i + 1);

            // 若 billEnd 超过合同结束时间，则设置为合同结束时间
            if (billEnd.isAfter(endDate)) {
                billEnd = endDate;
            }

            houseMonthlyBill.setBill_month(i + 1);
            houseMonthlyBill.setBill_start_date(Date.valueOf(billStart));
            houseMonthlyBill.setBill_end_date(Date.valueOf(billEnd));
            houseMonthlyBill.setCreated_at(Date.valueOf(LocalDate.now()));
            houseMonthlyBill.setUpdated_at(Date.valueOf(LocalDate.now()));
            houseMonthBillServiceImpl.insertHouseMonthBill(houseMonthlyBill);
        }
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

    @Override
    public void terminateLeaseContract(LeaseContract leaseContract) {
        if(1 == UserHolder.getUser().getRole())
            throw new RuntimeException("权限不足");
        if(!"occupied".equals(houseServiceImpl.getHouseById(leaseContract.getHouse_id()).getStatus()))
            throw new RuntimeException("房屋不处于已入住状态");

        leaseContract.setContract_status("terminated");
        leaseContractMapper.updateStatusById(leaseContract);
        houseServiceImpl.setOwnerNullByHouseNumber(leaseContract.getHouse_number());
        houseServiceImpl.updateStatusByHouseId(leaseContract.getHouse_id(), "vacant");

        logServiceImpl.insertLog(UserHolder.getUser().getId(), "终止合同" + leaseContract.getUser_id());
    }
}
