package com.apartmentsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
    private Integer id;
    private String title;
    private String content;
    private int created_by;
    private Date created_at;
    private Date updated_at;
}
