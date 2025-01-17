package com.apartmentsystem.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RequestUtil {

    public static Integer getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            return null;
        }
        Map<String, Object> claims = JWTutil.verifyToken(token);
        return (Integer) claims.get("id");
    }
}
