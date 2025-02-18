package com.apartmentsystem.controller;

import com.apartmentsystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Test
    public void testRegister() {
        User user = new User();
        user.setUsername("test111");
        user.setPassword("1456");
        user.setEmail("28022748500000@qq.com");
        user.setPhone("18242671223");
        user.setNick_name("danta");
        System.out.println(userController.register(user));
    }

    @Test
    public void testLogin() {
        System.out.println(userController.login("test1", "1456", 0));
    }
}
