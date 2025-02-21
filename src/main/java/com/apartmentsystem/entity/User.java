package com.apartmentsystem.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private int role; // 0: admin, 1: user 2: manager
    private String created_at;
    private String updated_at;
    private String photo;
    private String nick_name;
}
