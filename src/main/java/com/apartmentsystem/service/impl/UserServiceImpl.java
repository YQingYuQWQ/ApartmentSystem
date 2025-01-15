package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.Result;
import com.apartmentsystem.entity.User;
import com.apartmentsystem.mapper.UserMapper;
import com.apartmentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByUserName(String userName) {
        return null;
    }

    @Override
    public Result insertUser(String username, String password) {
        return null;
    }

    @Override
    public Result login(String username, String password) {
        return null;
    }

    @Override
    public Result updatePassword(String oldPassword, String newPassword, String rePassword, String token) {
        return null;
    }
}
