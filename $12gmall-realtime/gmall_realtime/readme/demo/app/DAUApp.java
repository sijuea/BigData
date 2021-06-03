//package com.demo.app;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.demo.constants.CommonConstants;
//import com.demo.entity.StartUpLog;
//import com.demo.utils.MykafkaUtil;
//import com.demo.utils.PhoenixUtils;
//import com.demo.utils.PropertiesUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.spark.api.java.JavaPairRDD;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.function.FlatMapFunction;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.PairFunction;
//import org.apache.spark.broadcast.Broadcast;
//import org.apache.spark.sql.SQLContext;
//import org.apache.spark.streaming.api.java.JavaDStream;
//import org.apache.spark.streaming.api.java.JavaInputDStream;
//import org.apache.spark.streaming.api.java.JavaStreamingContext;
//import org.apache.spark.streaming.kafka010.KafkaUtils;
//import redis.clients.jedis.Connection;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import scala.Tuple2;
//
//import javax.security.auth.login.Configuration;
//import java.sql.DriverManager;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
////导入phoenix提供的各种操作静态方法
//import org.apache.phoenix.*;
//
//import static java.time.LocalDate.*;
//
///**
// * @author sijue
// * @date 2021/5/28 9:57
// * @describe
// */
//@Slf4j
//public class DAUApp extends BaseApp {
//    Properties  config = PropertiesUtil.load("config.properties");
//
//
//    @Override
//    public void process(String appName,int interval) {
//        JavaStreamingContext sparkStreaming = createSparkStreaming(appName, interval);
//        //设置输出的日志级别
////        sparkStreaming.sparkContext().setLogLevel("INFO" );
//        //从kafka获取DataStream
//        //1.获取的数据是key-value结构，这里没有key值，需要value值即可
//        JavaInputDStream<ConsumerRecord<String , String>> stream =
//                MykafkaUtil.getKafkaStream(CommonConstants.KAFKA_START_LOG, sparkStreaming);
//        //2.连接kafka消费数据,获取kafka的value值，然后转为java对象
//        JavaDStream<StartUpLog> mapDS = stream.map(x -> {
//                    String json = x.value();
//
//            //将获取的每条json数据转换为对象
//            StartUpLog startUpLog = JSON.parseObject(json, StartUpLog.class);
//             //设置date和hour
//            DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            //传入日志的ts的时间戳
//            LocalDateTime date1=LocalDateTime.ofInstant(
//                    Instant.ofEpochMilli(startUpLog.getTs()), ZoneId.of("Asia/Shanghai"));
//            //获取ts转换之后的日期
//            String date = date1.format(ofPattern);
//            //获取小时
//            String hour=date1.getHour()+"";
//            startUpLog.setLogDate(date);
//            startUpLog.setLogHour(hour);
//                    return startUpLog;
//                }
//        );
//        mapDS.count().print();
//        //3.对数据进行去重，distinct--->groupby---->groupBykey，要求数据key-value结构
//        // 那么【key：时间和设备id，value：starupLog对象】
//        //逻辑：先分组然后再取第一条时间戳最小的
//        //3-1：DS调用transForm算子转换为RDD，然后调用groupByKey实现上面的逻辑
//        JavaDStream<StartUpLog> rdd2 = mapDS.transform((Function<JavaRDD<StartUpLog>, JavaRDD<StartUpLog>>) x -> {
//            JavaPairRDD<String, StartUpLog> keyValueRDD = x.mapToPair((PairFunction<StartUpLog, String, StartUpLog>) startUpLog -> {
//                return new Tuple2(startUpLog.getLogDate() + "_" + startUpLog.getMid(), startUpLog);
//            });
//            //对拼接之后的key-value进行分组排序
//            JavaPairRDD<String, Iterable<StartUpLog>> groupByRDD = keyValueRDD.groupByKey();
//            //排序取最新的
//            JavaRDD<StartUpLog> sortRDD = groupByRDD.map((Function<Tuple2<String, Iterable<StartUpLog>>, StartUpLog>) map -> {
//                List<StartUpLog> cltn = new ArrayList<>();
//                map._2.forEach(value1 -> cltn.add(value1));
//                cltn.sort((o1, o2) -> o2.getTs() > o1.getTs() ? 1 : -1);
//                return cltn.get(0);
//            });
//            return sortRDD;
//        });
//        //1.将上一步筛选出来的数据写到redis，key："DAU:"+日期，value:set<mid>
//        //2.获取redis数据，判断当前批数据的mid_id是否存在于redis指定key的set集合中
//            //方式一：removeDuplicateLog(rdd2);
//            //方式四：和前一个批次比较去重结束
//        JavaDStream<StartUpLog> rdd3 = removeDuplicateLog4(rdd2,sparkStreaming);
//        //缓存rdd3
//        rdd3.cache();
//        log.info("去重之后："+rdd3.count());
//        //3.跨批次去重之后要保存到redis,关闭redis连接
//        rdd3.foreachRDD(partition->{
//             Jedis jedis= new Jedis(config.getProperty("redis.host"),
//                     Integer.valueOf(config.getProperty("redis.port")));
//            partition.foreach(x->jedis.sadd("DAU:" + LocalDate.now(),x.getMid()));
//            jedis.close();
//        });
//            rdd3.foreachRDD(rdd->{
//
//            });
//         //java:将DateStream  转换为DataForm，然后通过df写入phoenix【
//        // 截止2021-05-31没有找到更好的解决方式，先使用scala吧？？】
//        // 4.将rdd过滤之后的结果写入Hbase[使用phoenix写入【phoneix建表：主键：logDate_mid】]
//        SQLContext sqlContext = new SQLContext(sparkStreaming.sparkContext());
////        rdd3foreachRDD(rdd->{
////            rdd.rdd().saveToPhoniex()foreachPartition(param->{
////                java.sql.Connection conn=PhoenixUtils.init();
////                while (param.hasNext()) {
////                    StartUpLog log=param.next();
////                    PhoenixUtils.insert(conn, log);
////                }
////                });
//
////            });
//        HBaseConnector
//        //关闭资源
//        stopTertina(sparkStreaming);
//    }
//
//    /**
//     * 方式一：每条数据都会建立一次连接性能不高
//     * @param rdd2
//     * @return
//     */
//    public JavaDStream<StartUpLog> removeDuplicateLog(JavaDStream<StartUpLog> rdd2
//                                                     ){
//        JavaDStream<StartUpLog> filter = rdd2.filter(
//                (Function<StartUpLog, Boolean>) tuple2 ->
//                {
//                    //创建redis连接
//                    Jedis jedis= new Jedis(config.getProperty("redis.host"),
//                            Integer.valueOf(config.getProperty("redis.port")));                    //sismember 判断指定key(第一个参数)的value里面是否包含某个值(第二个参数)
//                    Boolean sismember = jedis.sismember("DAU:" + tuple2.getLogDate(),
//                            tuple2.getMid());
//                    //关闭redis连接
//                    jedis.close();
//                    return !sismember;
//                });
//        return filter;
//    }
//
//    /**
//     * 方式二：分区为单位，每个分区建立一次redis连接
//     * 当前批次有N条启动日志，kafka指定的topic分区有M个，
//     *  那么SparkStreaming的数据分区也有M个，会从池中借M次，还M次，redis判断服务N次
//     *  【每个分区即先请求一次redis数据，然后判断】
//     *  【将第一次请求到的redis数据进行广播】
//     * @param rdd2
//     * @return
//     */
//    public JavaDStream<StartUpLog> removeDuplicateLog2(JavaDStream<StartUpLog> rdd2,
//                                                       JavaStreamingContext sparkStreaming) {
//       return rdd2.mapPartitions(
//                (FlatMapFunction<Iterator<StartUpLog>, StartUpLog>) partition -> {
//                    //创建redis连接;一个分区建立一次连接，分区的每条数据判断时都使用这条连接
//                    Jedis jedis= new Jedis(config.getProperty("redis.host"),
//                            Integer.valueOf(config.getProperty("redis.port")));                    while (partition.hasNext()){
//                        StartUpLog next = partition.next();
//                        //判断是否存在set集合中
//                        Boolean sismember =jedis.sismember("DAU:"+next.getLogDate(),next.getMid());
//                        if(sismember){
//                            partition.remove();
//                        }
//                    }
//                    //关闭redis连接
//                    jedis.close();
//                    return partition;
//                });
//    }
//
//    /**
//     * 方式三：
//     * 如果redis的并发负载高，无法及时处理大量的sismember请求，可以考虑将redis判断的逻辑放在spark客户端
//     *当前批次有N条启动日志，kafka指定的topic分区有M个，
//     *  那么SparkStreaming的数据分区也有M个，会从池中借M次，还M次，
//     *  每个服务只会向redis发送一次请求【即将请求的redis数据缓存起来】，共发送M次请求
//     * @param
//     */
//    public JavaDStream<StartUpLog> removeDuplicateLog3(JavaDStream<StartUpLog> rdd2,
//                                                       JavaStreamingContext sparkStreaming) {
//        return rdd2.mapPartitions(
//                (FlatMapFunction<Iterator<StartUpLog>, StartUpLog>) partition -> {
//                    //创建redis连接;一个分区建立一次连接，分区的每条数据判断时都使用这条连接
//                    Jedis jedis= new Jedis(config.getProperty("redis.host"),
//                            Integer.valueOf(config.getProperty("redis.port")));                    //获取redis数据
//                    Set<String> set = jedis.smembers("DAU:" + now());
//                    /**方式一：直接对分区内的数据进行判断：
//                     while (partition.hasNext()){
//                     StartUpLog next = partition.next();
//                     //判断是否存在set集合中
//                     Boolean sismember = set.contains(next.getMid());
//                     if(sismember){
//                     partition.remove();
//                     }
//                     }
//                     **/
//                    //方式二：设置广播变量
//                    Broadcast<Set<String>> broadcast = sparkStreaming.sparkContext().broadcast(set);
//                    //获取广播变量值[broadcast.value()]过滤
//                    while (partition.hasNext()){
//                        StartUpLog next = partition.next();
//                        //判断是否存在set集合中
//                        Boolean sismember = broadcast.value().contains(next.getMid());
//                        if(sismember){
//                            partition.remove();
//                        }
//                    }
//                    //关闭redis连接
//                    jedis.close();
//                    return partition;
//                });
//    }
//
//    /**
//     *：获取一次redis集合中的数据进行去重、修改，然后广播给其他批次，所以这里需要广播多次
//     *  即向redis发送一次连接和关闭，请求一次set集合，然后在spark端广播集合，
//     *      广播之后再判断当前批的数据是否存在set中
//     *   此处也不需要mapPartition，直接filter即可
//     * @param rdd2
//     * @param sparkStreaming
//     * @return
//     */
//    public JavaDStream<StartUpLog> removeDuplicateLog4(JavaDStream<StartUpLog> rdd2,
//                                                       JavaStreamingContext sparkStreaming) {
//        /**
//         * 以下写法不在DataStream中，所以这个写法是广播变量广播一次的写法
//         */
//        /**
//        //创建redis连接;
//         Jedis jedis= new Jedis(config.getProperty("redis.host"),
//         Integer.valueOf(config.getProperty("redis.port")));        //获取redis数据
//        Set<String> set = jedis.smembers("DAU:" + now());
//        //广播redis数据;
//        Broadcast<Set<String>> broadcast = sparkStreaming.sparkContext().broadcast(set);
//        //关闭jedis连接
//        return rdd2.filter((Function<StartUpLog, Boolean>) v1 ->
//             !broadcast.value().contains(v1.getMid())
//        );
//         **/
//        /*每次都广播的写法，即将广播写到DataStream的算子中*/
//        //sparkContext没有序列化，所以以下代码不能写到DataStream的算子中
//        return rdd2.transform((Function<JavaRDD<StartUpLog>, JavaRDD<StartUpLog>>) v1 -> {
//            //创建redis连接;
//            Jedis jedis= new Jedis(config.getProperty("redis.host"),
//                    Integer.valueOf(config.getProperty("redis.port")));
//            //获取redis数据
//            Set<String> set = jedis.smembers("DAU:" + now());
//            //广播redis数据;
//            Broadcast<Set<String>> broadcast = sparkStreaming.sparkContext().broadcast(set);
//            //过滤数据
//            JavaRDD<StartUpLog> filter = v1.filter(x -> broadcast.value().contains(x.getMid()));
//            //关闭jedis连接
//            jedis.close();
//            return filter;
//        } );
//    }
//
//    public static void main(String[] args) {
//        DAUApp dauApp = new DAUApp();
//        String appName="DAU";
//        int interval=10;
//        dauApp.process(appName,interval);
//    }
//
//
//
//}
