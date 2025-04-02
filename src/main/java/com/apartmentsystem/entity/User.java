package com.apartmentsystem.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    // 0: admin, 1: user 2: manager
    private int role;
    private String created_at;
    private String updated_at;
    private String photo;
    private String nick_name;
    private MultipartFile avatar;
}
