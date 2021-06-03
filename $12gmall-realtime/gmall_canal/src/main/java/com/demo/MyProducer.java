package com.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author sijue
 * @date 2021/5/31 0:26
 * @describe 生产者：向kafka里面写数据
 */
public class MyProducer {
    private static Producer<String,String > producer;
    static {
        //单例模式，只会创建一个对象
        producer=getKafkaProducer();
    }

    /**
     * 向kafka写数据
     * @param value
     * @param topic
     */
    public static void writeToKafka(String value,String topic){
        producer.send(new ProducerRecord<>(topic,value));
    }

    public static Producer<String ,String> getKafkaProducer(){
        //ProducerConfig
        Properties properties = new Properties();
        properties.put("bootstrap.servers","hadoop102:9092,hadoop103:9092,hadoop104:9092");
        //k-v序列化器
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<String,String>(properties);
    }
}
