package $01hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;

import java.io.IOException;


/*
    第二种创建文件系统对象的方式

    注意：访问HDFS的用户的配置：
         选中当前类的类名（如果找不到先运行一次） --> EditConfigurations -->VM Options -->
                -DHADOOP_USER_NAME=atguigu


    配置的优先级：
        客户端 ： ①代码  ②配置文件
        服务器端 ：①xxx-site.xml ②xxx-default.xml

     客户端代码 > 客户端配置文件  > 服务器端xxx-site.xml > 服务器端xxx-default.xml
 */
public class HDFSDemo2 {
    @Test
    public void test() throws IOException {

        Configuration conf = new Configuration();
        //通过配置文件的方式设置HDFS的地址
        conf.set("fs.defaultFS","hdfs://hadoop102:9820");
        conf.set("dfs.replication", "2");
        FileSystem fs = FileSystem.get(conf);


        /*
            参数 ： 目标路径
         */
        fs.copyFromLocalFile(false,
                true,new Path("D:\\io\\hdfs\\aa.txt"),new Path("/hdfs"));


        //关资源
        fs.close();

    }
}
