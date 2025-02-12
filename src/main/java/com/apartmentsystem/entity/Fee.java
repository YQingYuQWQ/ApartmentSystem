package com.apartmentsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fee {
    private int id;
    private String fee_number;
    private String house_number;
    private String type;
    private BigDecimal amount;
    private Date due_date;
    private boolean paid;
    private Timestamp created_at;
    private Timestamp updated_at;
    private boolean status;
}