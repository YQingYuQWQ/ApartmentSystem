package com.apartmentsystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Test
    public void testRegister() {
        System.out.println(userController.register("test1", "123456", "28022748533@qq.com", "18242671273", 0));
    }

    @Test
    public void testLogin() {
        System.out.println(userController.login("test1", "1456", 0));
    }
}
