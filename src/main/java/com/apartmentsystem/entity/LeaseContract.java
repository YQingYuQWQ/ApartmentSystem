package com.apartmentsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaseContract {
    private Integer id;
    private int house_id;
    private int user_id;
    private Date start_date;
    private Date end_date;
    private String contract_status;
    private BigDecimal monthly_rent;
    private BigDecimal deposit;
    private Timestamp created_at;
    private Timestamp updated_at;
}
