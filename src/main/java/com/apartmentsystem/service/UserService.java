package com.apartmentsystem.service;

import com.apartmentsystem.entity.User;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface UserService {
    User getUserByUserName(String userName);
    Boolean insertUser(String username, String password, String email, String phone, int role);
    String login(User user);
    Boolean updatePassword(String oldPassword, String newPassword, String rePassword, String token);
    void insertNormalUser(User user);
    User getUserInfo();
    List<User> getAllUser();
    void updateUserById(User user);
    void createUserById(User user);
    void deleteUserById(int id);
}
