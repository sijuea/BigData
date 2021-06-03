package com.demo.logandfastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.entity.User;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sijue
 * @date 2021/5/26 8:57
 * @describe  写日志的位置：在那些方法或者代码中打印日志
 *              获取logger
 *             日志级别：FATAL:致命错误
 *                      error：普通错误
                    warning：警告
                        INFO：普通信息、DEBUG：、TRACE
                    在配置文件配置日志级别
                            配置文件：log4j.xml(优先级高)或者log4j.properties
                            配置OGNL表达式
                日志存放的位置：文件，数据库，控制台
                    使用Appender负责将日志输出到Appender【一个配置文件可以输出多个appender】
                    log4j.appender.自定义名称=Appender实现类的.全类名
                            ConSoleAppender：向控制台输出的Appender
                            FileAppender：输出日志到文件
                记录格式：使用Layor配置每一行日志的输出格式
                        是Appender的属性，在APPENDER中可以setLayout(Layout layout)
                        layout输出样式：  %d输出日期 %p输出级别 %c 输出全类名  %m：输出的内容
                那些类采用那些格式生成日志
                    logger
                    log4j.logger【前缀】
                    log4ij.rootLogger:所有只要编写了logger的类，统一使用以下规则记录）=级别，appender名称
                            级别：默认记录级别及其级别之上的日志
                    如果log4j.logger配置在rootLogger之后，会覆盖rootLogger的配置
 */
public class LogAndFastJsonDemo {
    public static void main(String[] args) {
        User user=new User(1,"lisi",3);
        List<User> list=new ArrayList<>();
        list.add(user);
        //log
//        Logger logger = Logger.getLogger(LogAndFastJsonDemo.class);
        //对象转String
        String jsonString = JSON.toJSONString(user);
        System.out.println("对象转String"+jsonString);
        //array转string
        String usersStr = JSON.toJSONString(list);
        System.out.println("array转string"+usersStr);
        //str转java对象
        User u = JSON.parseObject(jsonString,User.class);
        System.out.println("str转java对象"+u);
        //str转JSONObject对象
        JSONObject jsonObject = JSON.parseObject(jsonString);
        System.out.println("jsonObject对象"+jsonObject.toString());
        //array转对象list
        List<User> list1 = JSON.parseArray(usersStr, User.class);
        System.out.println("array转对象list"+list1.toString());
        //str字符串转jsonArray
        JSONArray jsonArray=JSON.parseArray(usersStr);
        System.out.println("str字符串转jsonArray"+jsonArray);
        //从jsonArray获取json对象
        JSONObject json=jsonArray.getJSONObject(0);
        System.out.println("从jsonArray获取json对象"+json.toString());


        //从JSONObject获取数据
        Integer id = (Integer) json.get("id");
        System.out.println("取数操作"+id);
        //jsonObject写数据
        json.put("op","select");
        System.out.println("写数据之后的json"+json);


    }
}
