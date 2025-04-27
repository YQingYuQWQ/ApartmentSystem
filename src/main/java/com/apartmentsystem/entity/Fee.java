package com.apartmentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
    private Integer id;
    private String fee_number;
    private String house_number;
    private int user_id;
    private String type;
    private int house_monthly_bill_id;
    private BigDecimal amount;
    private Date due_date;
    private boolean paid;
    private Timestamp created_at;
    private Timestamp updated_at;
    private boolean status;
}