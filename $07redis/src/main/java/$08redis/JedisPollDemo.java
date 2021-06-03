package $08redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author sijue
 * @date 2021/4/12 10:13
 * @describe 连接池操作
 */
public class JedisPollDemo {
    public static void main(String[] args) {
        //对于redis的并发操作，使用Jedis的线程池操作
        JedisPoolConfig config = new JedisPoolConfig();
        //线程池中连接数的最大值
        config.setMaxTotal(10);
        //设置线程池中空闲的最大的连接数
        config.setMaxIdle(5);
        //设置线程池中空闲的最少的连接数
        config.setMaxIdle(2);
        //其他参数保持默认

        JedisPool jedisPool=new JedisPool("hadoop102",6379);
        //从线程池获取连接
        Jedis jedis = jedisPool.getResource();
        //将连接还给线程池
        jedis.close();
    }
}
