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
    }

    @Test
    public void testLogin() {
        System.out.println(userController.login("test1", "1456", 0));
    }
}
