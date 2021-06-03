package com.demo.utils;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.internal.LettuceClassUtils;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * @author sijue
 * @date 2021/5/28 9:47
 * @describe     //jedis旧版本 在多线程下面有bug,所以这个使用jedis3.3.x版本
 *              //但是也有错？有什么错，
 *              解决：不从连接池取数据，直接使用jedis连接redis即可
 *                  或者使用lettuce连接redis
 */

@Slf4j
public class RedisUtil {
   private  static JedisPool jedisPool;


    public static Jedis getJedisClient() {

        System.out.println("开辟一个连接池");
        Properties config  = PropertiesUtil.load("config.properties");
        String host  = config.getProperty("redis.host");
        String port  = config.getProperty("redis.port");

        JedisPoolConfig jedisPoolConfig = new  JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100); //最大连接数
        jedisPoolConfig.setMaxIdle(20); //最大空闲
        jedisPoolConfig.setMinIdle(20); //最小空闲
        jedisPoolConfig.setBlockWhenExhausted(true); //忙碌时是否等待
        jedisPoolConfig.setMaxWaitMillis(500); //忙碌时等待时长 毫秒
        jedisPoolConfig.setTestOnBorrow(true); //每次获得连接的进行测试
        jedisPoolConfig.setTestOnReturn(true); //每次将连接还入池子的时候，测试连接是否ok，ok再放回池子
        jedisPool = new JedisPool(jedisPoolConfig, host, Integer.valueOf(port));
        return jedisPool.getResource();
    }

    /**
     * 方式二：使用lettuce和common-pool创建连接池
     * @return pool
     * 注意点：
     * 以下使用常规连接，将使用之后的连接池的连接返回需要调用GenericObjectPool.returnObject(…)
     */
    public GenericObjectPool<StatefulRedisClusterConnection<String, String>> createLettuceConnect(){
        Properties config  = PropertiesUtil.load("config.properties");
        String host  = config.getProperty("redis.host");
        String port  = config.getProperty("redis.port");
        RedisClusterClient clusterClient = RedisClusterClient.create(
                RedisURI.create(host, Integer.valueOf(port)));
        //连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(100); //最大连接数
        poolConfig.setMaxIdle(20); //最大空闲
        poolConfig.setMinIdle(20); //最小空闲
        poolConfig.setBlockWhenExhausted(true); //忙碌时是否等待
        poolConfig.setMaxWaitMillis(500); //忙碌时等待时长 毫秒
        poolConfig.setTestOnBorrow(true); //每次获得连接的进行测试
        poolConfig.setTestOnReturn(true); //每次将连接还入池子的时候，测试连接是否ok，ok再放回池子
        GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool = ConnectionPoolSupport
                .createGenericObjectPool(() -> clusterClient.connect(), poolConfig);
        return pool;

    }
    public void close( GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool,RedisClusterClient client){
        if(pool!=null){
            pool.close();
        }
        if(client!=null){
            client.shutdown();
        }

    }
    public static void main(String[] args) {
        //方式二的使用方式：
        // executing work
        GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool = new RedisUtil().createLettuceConnect();
        try (StatefulRedisClusterConnection<String, String> connection = pool.borrowObject()) {
            connection.sync().set("key", "value");
            connection.sync().blpop(10, "list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

