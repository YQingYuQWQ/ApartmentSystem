package com.apartmentsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseMonthlyBill {
    private Integer id;
    private int bill_month;
    private Date bill_start_date;
    private Date bill_end_date;
    private int house_id;
    private String house_number;
    private BigDecimal monthly_rent;
    private Boolean rent_status;
    private BigDecimal monthly_property_fee;
    private Boolean property_fee_status;
    private Date created_at;
    private Date updated_at;
}
