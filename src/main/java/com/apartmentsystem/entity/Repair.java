package com.apartmentsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repair {
    private Integer id;
    private int house_id;
    private String description;
    private String status;
    private Date created_at;
    private Date updated_at;
}
