package com.apartmentsystem.service;

import com.apartmentsystem.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    User getUserByUserName(String userName);
    User getUserById(int id);
    Boolean insertUser(String username, String password, String email, String phone, int role);
    String login(User user);
    Boolean updatePassword(String oldPassword, String newPassword, String rePassword, String token);
    void insertNormalUser(User user);
    User getUserInfo();
    List<User> getAllUser();
    List<User> getByIds(List<Integer> ids);
    void updateUserById(User user);
    void updateUserAvatar(MultipartFile file);
    void createUserById(User user);
    void deleteUserById(int id);
}
