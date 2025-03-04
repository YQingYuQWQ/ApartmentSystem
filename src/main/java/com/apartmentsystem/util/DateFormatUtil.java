package com.apartmentsystem.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class DateFormatUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 将 Date 转换为指定格式的字符串
     * @param date 需要转换的 Date 对象
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * 计算两个日期之间的月份数
     *
     * @param startDate 起始日期
     * @param endDate   结束日期
     * @return 月份数
     */
    public static long calculateMonthsBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.MONTHS.between(startDate, endDate);
    }
}
