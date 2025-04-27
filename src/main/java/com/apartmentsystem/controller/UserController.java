package com.apartmentsystem.controller;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.entity.User;
import com.apartmentsystem.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/user")
@CrossOrigin
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

    @GetMapping("/getAllUser")
    public Result getAllUser() {
        return Result.success(userServiceImpl.getAllUser());
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        userServiceImpl.updateUserById(user);
        return Result.success();
    }

    @PostMapping("/createUser")
    public Result createUser(@RequestBody User user) {
        userServiceImpl.createUserById(user);
        return Result.success();
    }

    @PostMapping("/getUserById")
    public Result getUserById(@RequestParam int id) {
        return Result.success(userServiceImpl.getUserById(id));
    }

    @PostMapping("/getUserByIds")
    public Result getUserByIds(@RequestBody List<Integer> user_ids) {
        return Result.success(userServiceImpl.getByIds(user_ids));
    }

    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestBody User user) {
        userServiceImpl.deleteUserById(user.getId());
        return Result.success();
    }

    @PostMapping("/updateUserAvatar")
    public Result updateUserAvatar(@RequestParam("avatar") MultipartFile file) {
        userServiceImpl.updateUserAvatar(file);
        return Result.success();
    }
}
