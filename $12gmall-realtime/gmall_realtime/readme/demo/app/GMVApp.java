package com.demo.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.constants.CommonConstants;
import com.demo.entity.OrderInfo;
import com.demo.entity.StartUpLog;
import com.demo.utils.MykafkaUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author sijue
 * @date 2021/5/31 11:32
 * @describe
 */
    @Override
    public void process(String appName, int interval) {
        JavaStreamingContext streaming = createSparkStreaming(appName, interval);
        //读取kafka数据处理
        JavaInputDStream<ConsumerRecord<String, String>> stream = MykafkaUtil.getKafkaStream(CommonConstants.KAFKA_ORDER_INFO, streaming);
        JavaDStream<OrderInfo> mapDS = stream.map(x -> {
            String json = x.value();
            OrderInfo orderInfo = JSON.parseObject(json, OrderInfo.class);
            //create_date 和create_hour赋值
            DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date1=LocalDateTime.parse(orderInfo.getCreate_time(),ofPattern);
            //赋值
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            orderInfo.setCreate_date(date1.format(formatter));
            orderInfo.setCreate_hour(String.valueOf(date1.getHour()));
            //对手机号脱敏
            orderInfo.setConsignee_tel(orderInfo.getConsignee_tel().replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2"));
            return orderInfo;
        });
        //写入Hbase
            

        //streaming 开始和阻塞
        stopTertina(streaming);



    }

    public static void main(String[] args) {
        String appName="GMVApp";
        int interval=10;
        new  GMVApp().process(appName,interval);
    }
}
