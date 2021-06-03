//package com.demo.app;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.spark.SparkConf;
//import org.apache.spark.streaming.Durations;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//
///**
// * @author sijue
// * @date 2021/5/28 9:08
// * @describe
// */
//@Slf4j
//public abstract  class BaseApp {
//
//
//    /**
//     * 创建sparkContext
//     * @param appName 创建每个程序的appName不一样
//     * @param interval 每个程序的每个批次之间的间隔也可能不一样
//     * @return
//     */
//    public  JavaStreamingContext createSparkStreaming(String appName,int interval){
//          SparkConf  conf = new SparkConf().setMaster("local[4]").setAppName(appName);
//        JavaStreamingContext javaStreamingContext=new JavaStreamingContext(conf, Durations.seconds(interval));
//        return javaStreamingContext;
//    }
//    public  void  stopTertina(JavaStreamingContext javaStreamingContext)  {
//        try {
//            javaStreamingContext.start();
//            javaStreamingContext.awaitTermination();
//        }catch (Exception e){
//            log.info("spark start error");
//        }finally {
//            javaStreamingContext.close();
//        }
//
//    }
//    public abstract void process(String appName,int interval);
//}
