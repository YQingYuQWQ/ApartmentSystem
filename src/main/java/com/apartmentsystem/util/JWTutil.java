package com.apartmentsystem.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JWTutil {
    private static final String key = "baoyuan";

    //接受业务数据，生成token
    public static String genToken(Map<String, Object> claims){
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256(key));
    }

    //接受token，验证token，并返回业务数据
    public static Map<String, Object> verifyToken(String token){
        return JWT.require(Algorithm.HMAC256(key))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}
