package com.demo.tdemo.day01;

import com.google.common.collect.Iterables;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.*;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author sijue
 * @date 2021/5/24 23:07
 * @describe
 */
public class WordCountJava {
    public static void main(String[] args) {
        JavaSparkContext sc=
                new JavaSparkContext(new SparkConf().setMaster("local[4]").setAppName("test"));
        //读取文件
        JavaRDD<String> rdd = sc.textFile("$09spark/datas/wc.txt");
        //按空格切分
        JavaRDD<String> flatMap = rdd.flatMap((FlatMapFunction<String, String>) s -> Arrays.asList(s.split(" ")).iterator());
        //group by
        JavaPairRDD<String, Iterable<String>> rdd2 = flatMap.groupBy(x -> x);
        //map
        JavaRDD<Tuple2<String, Integer>> rdd3 = rdd2.map((Function<Tuple2<String, Iterable<String>>, Tuple2<String, Integer>>) v1 ->
                new Tuple2(v1._1, Iterables.size(v1._2)));
        rdd3.collect().forEach(x-> System.out.println(x));

    }
}
