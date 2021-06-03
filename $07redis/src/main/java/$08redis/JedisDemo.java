package $08redis;

import redis.clients.jedis.Jedis;

/**
 * @author sijue
 * @date 2021/4/12 10:09
 * @describe redis单个连接操作
 */
public class JedisDemo {
    public static void main(String[] args) {
        //创建jedis客户端
        Jedis jedis=new Jedis("hadoop102",6379);
        //设置key
        jedis.set("name","1111");
        //设置多个key
        jedis.mset("k1","v1","k2","v2");
        //关闭jedis
        jedis.close();
    }
}
