package com.apartmentsystem.service.impl;

import com.apartmentsystem.entity.User;
import com.apartmentsystem.mapper.UserMapper;
import com.apartmentsystem.service.UserService;
import com.apartmentsystem.util.JWTutil;
import com.apartmentsystem.util.PasswordUtil;
import com.apartmentsystem.util.UserHolder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private LogServiceImpl logServiceImpl;

    @Override
    public User getUserByUserName(String userName) {
        return userMapper.getUserByUserName(userName);
    }

    /*
        * 注册用户
        * @param username 用户名
        * @param password 密码
    */
    @Override
    public Boolean insertUser(String username, String password, String email, String phone, int role) {
        if (userMapper.getUserByUserName(username) != null)
            throw new RuntimeException("用户名已存在");
        if (userMapper.getUserByEmail(email) != null)
            throw new RuntimeException("邮箱已存在");
        if (userMapper.getUserByPhone(phone) != null)
            throw new RuntimeException("手机号已存在");
        String encryptPassword = PasswordUtil.encryptPassword(password);
        userMapper.insertUser(username, encryptPassword, email, phone, role);
        return true;
    }

    /*
        * 用户登录
        * @param username 用户名
        * @param password 密码
    */
    @Override
    public String login(User user_login) {
        User user = userMapper.getUserAllByUserName(user_login.getUsername());
        if (user == null)
            throw new RuntimeException("用户不存在");
        if (!passwordUtil.matches(user_login.getPassword(), user.getPassword()))
            throw new RuntimeException("密码错误");
        if  (user_login.getRole() != user.getRole())
            throw new RuntimeException("角色错误");

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        String token = JWTutil.genToken(claims);
        //将token存入redis
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(token, token , 12, TimeUnit.HOURS);

        logServiceImpl.insertLog(user.getId(), "登录");
        return token;
    }

    /*
        * 修改密码
        * @param oldPassword 旧密码
        * @param newPassword 新密码
        * @param rePassword 重复密码
        * @param token token
    */
    @Override
    public Boolean updatePassword(String oldPassword, String newPassword, String rePassword, String token) {
        Map<String, Object> claims = JWTutil.verifyToken(token);
        Object username = claims.get("username");
        User user = userMapper.getUserByUserName(username.toString());
        if (!passwordUtil.matches(oldPassword, user.getPassword()))
            throw new RuntimeException("旧密码错误");
        if (!newPassword.equals(rePassword))
            throw new RuntimeException("两次密码不一致");

        logServiceImpl.insertLog(UserHolder.getUser().getId(), "修改密码");
        userMapper.updateUserPasswordById(user.getId(), PasswordUtil.encryptPassword(newPassword));
        return true;
    }

    @Override
    public void insertNormalUser(@NotNull User user) {
        if(userMapper.getUserByUserName(user.getUsername()) != null)
            throw new RuntimeException("用户名已存在");
        if(userMapper.getUserByEmail(user.getEmail()) != null)
            throw new RuntimeException("邮箱已存在");
        if(userMapper.getUserByPhone(user.getPhone()) != null)
            throw new RuntimeException("手机号已存在");
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
        if(!userMapper.insertNormalUser(user))
            throw new RuntimeException("注册失败");
    }

    @Override
    public User getUserInfo() {
        return userMapper.getUserByUserName(UserHolder.getUser().getUsername());
    }
}
