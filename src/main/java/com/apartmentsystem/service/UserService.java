package com.apartmentsystem.service;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.entity.User;

import javax.validation.constraints.Pattern;

public interface UserService {
    User getUserByUserName(String userName);
    Result insertUser(String username, String password, String email, String phone, int role);
    Result login(String username, String password, int role);
    Result updatePassword(String oldPassword, String newPassword, String rePassword, String token);
}
