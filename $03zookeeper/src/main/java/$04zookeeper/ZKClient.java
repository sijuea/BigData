package $04zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author sijue
 * @date 2021/3/16 10:21
 * @describe
 */
public class ZKClient {
    private static String connect="hadoop102:2181,hadoop103:2181,hadoop104:2181";
    private static ZooKeeper zk=null;
    public static void main(String[] args) {
        try {
            //创建连接
           zk=new ZooKeeper(connect, 4000, new Watcher() {
               public void process(WatchedEvent event) {

               }
           });
           //判断监听父节点是否存在
            Stat exists = zk.exists("/node", false);
            if (exists==null){
                zk.create("/node","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }else{
                //对子节点进行监听
                register();
            }
            Thread.sleep(Long.MAX_VALUE);
        }catch (Exception e){
           e.printStackTrace();
       }finally {
           if(zk!=null){
               try {
                   zk.close();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }
    }

    public static void register() throws Exception {
        List<String> children = zk.getChildren("/node", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("节点改变");
                //继续进行监听
                try {
                    register();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        for (String str:children) {
            System.out.println(str);
        }
    }
}
