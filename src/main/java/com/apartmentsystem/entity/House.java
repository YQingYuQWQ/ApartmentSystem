package com.apartmentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class House {
    private Integer id;
    private String house_number;
    private String building_name;
    private int floor;
    private Integer owner_id;
    private String status;

    @TableField(exist = false)
    private String created_at;
    @TableField(exist = false)
    private String updated_at;

    private BigDecimal water_fee;
    private BigDecimal power_fee;
    private BigDecimal deposit;
    private BigDecimal price;
    private int area;
    private String images;
    private String images_info;
}
