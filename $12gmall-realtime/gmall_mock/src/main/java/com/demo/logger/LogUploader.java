package com.demo.logger;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author sijue
 * @date 2021/5/26 10:57
 * @describe
 */
public class LogUploader {
    //发送日志，使用http请求
    public static void sendLogStream(String log){
        try{
            //不同的日志类型对应不同的URL,发送日志的地址
//            URL url  =new URL("http://hadoop104:81/gmall/log");
            URL url  =new URL("http://localhost:8083/log");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //设置请求方式为post
            conn.setRequestMethod("POST");

            //时间头用来供server进行时钟校对的
            conn.setRequestProperty("clientTime",System.currentTimeMillis() + "");

            //允许上传数据
            conn.setDoOutput(true);

            //模拟发送表单
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            System.out.println("upload" + log);

            //输出流
            OutputStream out = conn.getOutputStream();
            //发送属性名[logString]和属性值
            out.write(("logString="+log).getBytes());
            out.flush();
            out.close();
            //获取响应码:200:ok
            //4xx:客户端错误： 404 ：路径找不到；400：参数格式不符合服务器要求；405：请求方式不符合要求
            //5xx：服务器异常【java运行程序问题】
            int code = conn.getResponseCode();
            System.out.println(code);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
