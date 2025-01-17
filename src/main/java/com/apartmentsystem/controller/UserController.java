package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.service.impl.LogServiceImpl;
import com.apartmentsystem.service.impl.UserServiceImpl;
import com.apartmentsystem.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private LogServiceImpl logServiceImpl;

    @PostMapping("/register")
    public Result register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam int role,
                           HttpServletRequest request) {
        if(RequestUtil.getUserIdFromRequest(request) != null)
            logServiceImpl.insertLog(RequestUtil.getUserIdFromRequest(request), "新增用户:" + username);
        return userServiceImpl.insertUser(username, password, email, phone, role);
    }

    @PostMapping("/login")
    public Result login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam int role) {
        return userServiceImpl.login(username, password, role);
    }
}
