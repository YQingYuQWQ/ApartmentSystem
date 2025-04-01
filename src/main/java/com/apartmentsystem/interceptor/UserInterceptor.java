package com.apartmentsystem.interceptor;


import com.apartmentsystem.entity.User;
import com.apartmentsystem.mapper.UserMapper;
import com.apartmentsystem.util.JWTutil;
import com.apartmentsystem.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            System.out.println("Method:OPTIONS");
            return true;
        }
        //令牌验证
        String token = request.getHeader("Authorization");
        if (token == null) {
            response.setStatus(401);
            return false;
        }
        //从redis中获取token
        String redisToken = stringRedisTemplate.opsForValue().get(token);
        if (redisToken == null || !redisToken.equals(token)) {
            response.setStatus(401);
            return false;
        }

        User user = userMapper.getUserByUserName(JWTutil.verifyToken(token).get("username").toString());
        if(user == null)
            return true;
        UserHolder.saveUser(user);
        return true;
    }
}
