package $08redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sijue
 * @date 2021/4/12 15:54
 * @describe
 */
public class JedisClusterDemo {
    /**
     * 连接redis集群
     * @param args
     */
    public static void main(String[] args) {
        Set<HostAndPort> set=new HashSet<HostAndPort>();
        set.add(new HostAndPort("192.168.1.102", 6379));
        set.add(new HostAndPort("192.168.1.102",6380));
        set.add(new HostAndPort("192.168.1.102",6381));
        //线程池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(2);
        JedisCluster cluster = new JedisCluster(set,50000,poolConfig);
         cluster.mset("{a}k111", "v1k1", "{a}k222", "v2");
        List<String> mget = cluster.mget("{a}k111", "{a}k222");
        System.out.println(mget.toString());
    }
}
