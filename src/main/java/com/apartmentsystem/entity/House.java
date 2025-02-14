package com.apartmentsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {
    private int id;
    private String house_number;
    private String building_name;
    private int floor;
    private Integer owner_id;
    private String status;
    private String created_at;
    private String updated_at;
    private BigDecimal water_fee;
    private BigDecimal power_fee;
    private BigDecimal price;
    private int area;
    private String images;
}
