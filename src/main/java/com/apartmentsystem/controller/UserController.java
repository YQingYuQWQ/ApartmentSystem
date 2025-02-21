package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.entity.User;
import com.apartmentsystem.service.impl.LogServiceImpl;
import com.apartmentsystem.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public Result register(@RequestBody User user){
        userServiceImpl.insertNormalUser(user);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return Result.success(userServiceImpl.login(user));
    }

    @PostMapping("/getUserInfo")
    public Result getUserInfo() {
        return Result.success(userServiceImpl.getUserInfo());
    }

}
