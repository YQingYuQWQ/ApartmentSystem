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
        return userServiceImpl.insertNormalUser(user);
    }

    @PostMapping("/login")
    public Result login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam int role) {
        return userServiceImpl.login(username, password, role);
    }

    @PostMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request) {
        return Result.success(userServiceImpl.getUserInfo(request));
    }

}
