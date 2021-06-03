package com.demo.$04zookeeper;

import org.apache.zookeeper.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author sijue
 * @date 2021/3/12 15:50
 * @describe
 */
public class ZkTest {
    ZooKeeper zkClient;
    @Before
    public void init() throws IOException {
        String connectString="hadoop102:2181,hadoop103:2181,hadoop103:2181";
       zkClient=new ZooKeeper(connectString, 400, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println(event.getType()+"---"+event.getPath());
                //重新进行监听
                try {
                    zkClient.getChildren("/",true);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取子节点信息
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void getNode() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/", true);
        for (String s:children) {
            System.out.println(s);
        }
        //延时阻塞
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 创建子节点
     */
    @Test
    public void createChild() throws KeeperException, InterruptedException {
        String nodeCreated=
                zkClient.create("/honglou","jiabaoyu".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL,CreateMode.PERSISTENT);
        System.out.println("craete"+nodeCreated);
    }

    /**
     * 关闭资源
     */
    @After
    public  void close(){
        if(zkClient!=null){
            try {
                zkClient.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
