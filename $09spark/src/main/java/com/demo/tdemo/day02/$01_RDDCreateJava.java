package com.demo.tdemo.day02;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.Test;

import java.util.*;

/**
 * @author sijue
 * @date 2021/5/25 18:54
 * @describe
 */
public class $01_RDDCreateJava {

    JavaSparkContext sc=new JavaSparkContext(
            new SparkConf().setAppName("master").setMaster("local[4]"));
    /**
     * RDD的创建:
     *      1、根据本地集合创建
     *      2、读取文件创建
     *      3、根据其他RDD衍生
     */
    @Test
    public void createRdd(){
        List<Integer> list=new ArrayList<>();
        Collections.addAll(list,1,2,3,4,5);
        List<Integer> list1 = Arrays.asList(1,2,3);
        //从list创建
        JavaRDD<Integer> parallelize = sc.parallelize(list1);
        //读取文件创建
        JavaRDD<String> textFile = sc.textFile("datas/wc.txt");
        //从其他RDD衍生
        JavaRDD<Integer> union = sc.union(parallelize);
        parallelize.collect().forEach(x-> System.out.println(x));
        textFile.collect().forEach(x-> System.out.println(x));
        union.collect().forEach(x-> System.out.println(x));
    }
}
