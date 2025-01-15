package com.apartmentsystem.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private int role; // 0: admin, 1: user 2: manager
    private String createTime;
    private String updateTime;
}
