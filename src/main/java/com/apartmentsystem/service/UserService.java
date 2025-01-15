package com.apartmentsystem.service;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.entity.User;

import javax.validation.constraints.Pattern;

public interface UserService {
    User getUserByUserName(String userName);
    Result insertUser(@Pattern(regexp = "^[a-zA-Z0-9]{5,16}$") String username, @Pattern(regexp = "^[a-zA-Z0-9]{5,16}$") String password);
    Result login(@Pattern(regexp = "^[a-zA-Z0-9]{5,16}$")String username, @Pattern(regexp = "^[a-zA-Z0-9]{5,16}$") String password);
    Result updatePassword(String oldPassword, String newPassword, String rePassword, String token);
}
