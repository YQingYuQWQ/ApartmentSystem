package com.apartmentsystem.util;

import com.apartmentsystem.entity.User;

public class UserHolder {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void saveUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        User user = userThreadLocal.get();
        if (user == null) {
            throw new NullPointerException("未获取到线程中的用户.");
        }
        return user;
    }

    public static void removeUser() {
        userThreadLocal.remove();
    }
}
