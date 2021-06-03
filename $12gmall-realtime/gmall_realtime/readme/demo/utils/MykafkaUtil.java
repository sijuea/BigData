package com.demo.utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.dstream.InputDStream;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author sijue
 * @date 2021/5/28 9:19
 * @describe sparkContext 使用直连模式从kafka获取数据
 */
public class MykafkaUtil {

    // 创建DStream，返回接收到的输入数据
    // LocationStrategies：根据给定的主题和集群地址创建consumer
    // LocationStrategies.PreferConsistent：持续的在所有Executor之间分配分区
    // ConsumerStrategies：选择如何在Driver和Executor上创建和配置Kafka Consumer
    // ConsumerStrategies.Subscribe：订阅一系列主题
   public static JavaInputDStream<ConsumerRecord<String, String>>  getKafkaStream(String topic , JavaStreamingContext ssc ) {
        //1.创建配置信息对象
         Properties properties = PropertiesUtil.load("config.properties");
        //设置kafka参数
        Map<String,Object> map=new HashMap<>();
        //2.用于初始化链接到集群的地址
        String broker_list  = properties.getProperty("kafka.broker.list");
        map.put("bootstrap.servers",broker_list);
        //在config.properties中设置的是string类型的序列化
        map.put("key.deserializer", StringDeserializer.class);
        map.put("value.deserializer",StringDeserializer.class);
        //消费者组
        map.put("group.id","bigdata2021");
        //如果没有初始化偏移量或者当前的偏移量不存在任何服务器上，可以使用这个配置属性
        //可以使用这个配置，latest自动重置偏移量为最新的偏移量
        //earliest：从分区的最开始读数据，
        //latest：读取分区的最新数据
        map.put("auto.offset.reset","earliest");
        //如果是true，则这个消费者的偏移量会在后台自动提交,但是kafka宕机容易丢失数据
        //如果是false，会需要手动维护kafka偏移量
        map.put("enable.auto.commit",true);
        JavaInputDStream<ConsumerRecord<String, String>> dStream = KafkaUtils.createDirectStream(ssc,
                //位置策略：executor和kafka的broker的位置关系；
                // 以下配置将kafka分区平均分配给可用的executor执行者；
                LocationStrategies.PreferBrokers(),
                //设置kafka非独立消费者
                ConsumerStrategies.Subscribe(Arrays.asList(topic),map));

        return dStream;
    }

}
