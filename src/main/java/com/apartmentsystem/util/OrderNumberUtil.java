package com.apartmentsystem.util;

import io.netty.util.internal.ThreadLocalRandom;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class OrderNumberUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final int RANDOM_NUM_BOUND = 10000;
    public static String generateOrderNumber(String prefix) {
        String timestamp = dateFormat.format(new Date());
        int randomNumber = ThreadLocalRandom.current().nextInt(RANDOM_NUM_BOUND);
        return prefix + timestamp + String.format("%04d", randomNumber);
    }
}
