package com.demo.homework;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Serializable;
import scala.Tuple2;
import scala.Tuple3;

import java.util.*;

/**
 * @author sijue
 * @date 2021/5/20 17:15
 * @describe
 */
public class SparkTest06 {

    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setAppName("test").setMaster("local[4]"));
        JavaRDD<String> rdd1 = sc.textFile("$09spark/datas/user_visit_action.txt");
        //用户行为包括搜索、点击、下单和支付、首先过滤掉搜索的数据
        JavaRDD<String> filter = rdd1.filter(x -> {
            String[] s = x.split("_");
            return s[5] .equals( "null");
        });

        //统计下单，支付，统计的数量
        JavaPairRDD<String, Tuple3<Integer, Integer, Integer>> mapJavaRDD =
                filter.flatMapToPair(x->{
                    List<Tuple2<String, Tuple3<Integer, Integer, Integer>>> list=new ArrayList<>();
                    String []split=x.split("_");
                    String clickId = split[6];
                    String orderId = split[8];
                    String payId = split[10];
                    if(!clickId.equals("-1")){
                        Tuple2<String, Tuple3<Integer, Integer, Integer>> tuple2 =
                                new Tuple2<>(clickId, new Tuple3<>(1, 0, 0));
                        list.add(tuple2);
                    }
                    if(!orderId.equals("null")){
                        List<String> strings = Arrays.asList(orderId.split(","));
                        for(int i=0;i<strings.size();i++){
                            Tuple2<String, Tuple3<Integer, Integer, Integer>> order =
                                    new Tuple2<>(strings.get(i), new Tuple3<>(0, 1, 0));
                            list.add(order);
                        }
                    }
                    if(!payId.equals("null")){
                        List<String> strings = Arrays.asList(payId.split(","));
                        for(int i=0;i<strings.size();i++){
                            Tuple2<String, Tuple3<Integer, Integer, Integer>> pay =
                                    new Tuple2<>(strings.get(i), new Tuple3<>(0, 0, 1));
                            list.add(pay);
                        }
                    }
                    return list.iterator();
                });
        JavaPairRDD<String, Tuple3<Integer, Integer, Integer>> rdd3 = mapJavaRDD.reduceByKey((agg, cur) -> new Tuple3<>(agg._1() + cur._1(),
                agg._2() + cur._2(), agg._3() + cur._3()));
        JavaPairRDD<Tuple3<Integer, Integer, Integer>, String> take =
                rdd3.mapToPair((x -> new Tuple2<>(x._2, x._1)))
                        .sortByKey(new TupleComparator(),false);
        take.collect().subList(0,10).forEach(x-> System.out.println(x));
    }
}
class TupleComparator implements Comparator<Tuple3<Integer, Integer, Integer>>, Serializable {

    @Override
    public int compare(Tuple3<Integer, Integer, Integer> o1, Tuple3<Integer, Integer, Integer> o2) {
         if(o1._1().compareTo(o2._1())==0){
             if(o1._2().compareTo(o2._2())==0){
                 return o1._3().compareTo(o2._3());
             }else{
                 return o1._2().compareTo(o2._2());
             }
         } else {
             return o1._1().compareTo(o2._1());
         }


    }
}
