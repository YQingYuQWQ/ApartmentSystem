package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public Result register(String username, String password, String email, String phone, int role) {
        return userServiceImpl.insertUser(username, password, email, phone, role);
    }

    @PostMapping("/login")
    public Result login(String username, String password, int role) {
        return userServiceImpl.login(username, password, role);
    }
}
