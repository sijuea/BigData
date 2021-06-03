package $08redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sijue
 * @date 2021/4/12 15:33
 * @describe
 */
public class JedisSentinel {
    /**
     * 连接哨兵主从模式
     * @param args
     */
    public static void main(String[] args) {
        Set<String> set = new HashSet<String>();
        set.add("hadoop102:26379");
        //线程池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(2);
        JedisSentinelPool sentinelPool =
                new JedisSentinelPool("mymaster",set,poolConfig);
        Jedis jedis = sentinelPool.getResource();
        System.out.println(jedis.ping());
        jedis.close();
    }

}
