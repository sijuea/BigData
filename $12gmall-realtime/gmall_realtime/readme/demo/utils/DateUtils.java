package com.demo.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author sijue
 * @date 2021/5/30 12:43
 * @describe 基于毫秒的时间戳，获取当日的日期和当日的小时时间
 *
 *            新-----------------------------旧
 *          DateTimeFormatter             SimpleDateFormat
 *     Date  LocalDateTime                LocalDate
 *     通常使用新的
 */
public class DateUtils {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH");

        Date date = new Date(System.currentTimeMillis());
        //jdk1.8版本之前的api，多线程时使用有问题
        System.out.println(format.format(date));
        System.out.println(format1.format(date));
        System.out.println("---------------------------------------------");
        //jdk1.8版本使用新的api
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter ofPattern1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        //ofEpochMilli参数是基于毫秒的时间戳
        LocalDateTime date1=LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.of("Asia/Shanghai"));
        String format2 = date1.format(ofPattern);
        System.out.println("小时:"+date1.getHour());
    }
}
