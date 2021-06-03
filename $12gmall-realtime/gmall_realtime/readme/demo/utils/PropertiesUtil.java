package com.demo.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author sijue
 * @date 2021/5/28 9:16
 * @describe
 */

public class PropertiesUtil {
    public static Properties load(String propertieName) {
        Properties prop=new Properties();
        try {
            prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertieName) , "UTF-8"));
        } catch (IOException e) {

        }
        return prop;
    }

    public static void main(String[] args) {
        Properties load = PropertiesUtil.load("config.properties");
        System.out.println(load.getProperty("redis.port"));
    }

}
