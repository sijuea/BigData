package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.constants.CommonConstants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sijue
 * @date 2021/5/26 11:18
 * @describe
 */
@RestController
@Slf4j //自动在容器中创建日志对象  Logger log=logger.getLogger(xxx.class)
public class LogController {
    @Autowired     // 只要引入了spring-kafka，容器会自动创建一个KafkaTemplate<?,?>的对象
    KafkaTemplate<String,String> kafkaProduce;
    //测试接受日志
    @RequestMapping("/log")
    public  String test(String logString){
        //解析日志为jsonObject
        JSONObject jsonObject= JSON.parseObject(logString);
        jsonObject.put("ts",System.currentTimeMillis());
        log.info("jsonString:"+jsonObject.toJSONString());
        //根据不同的日志类型，生产到不同的主题
        if(jsonObject.get("type").equals("startup")){
            kafkaProduce.send(CommonConstants.KAFKA_START_LOG,jsonObject.toJSONString());
        }else if(jsonObject.get("type").equals("event")){
            kafkaProduce.send(CommonConstants.KAFKA_EVENT_LOG,jsonObject.toJSONString());
        }
        return "success";
    }

    @GetMapping("/hello")
    public String handle(){
       log.info("hello");
        return "success";
    }
}
