package $04zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

/**
 * @author sijue
 * @date 2021/3/16 10:21
 * @describe
 */
public class ZkServer {
private static String connect="hadoop102:2181,hadoop103:2181,hadoop104:2181";
    public static void main(String[] args) {
        ZooKeeper zk=null;
        try {
            //创建连接
            zk=new ZooKeeper(connect, 4000, new Watcher() {
                public void process(WatchedEvent event) {

                }
            });
            //判断父节点是否存在
            Stat exists = zk.exists("/node", false);
            if(exists==null){
                zk.create("/node","".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }else{
                //创建临时子节点
                zk.create("/node/"+args[0],args[1].getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
            }
            //延迟主程序关闭
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
}
