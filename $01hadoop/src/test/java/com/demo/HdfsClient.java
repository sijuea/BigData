package com.demo;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author sijue
 * @date 2021/3/3 20:40
 * @describe
 */
public class HdfsClient {
    /**配置数据优先级从高到低：（1）（2）（3）（4）
     * 客户端：（1）代码，（2）配置文件
     * 服务器：（3）xxxx-site.xml  （4）xxx-default.xml
     */
    /**基本步骤
     * 1.获取fs配置连接hdfs
     * 2.文件操作：创建文件夹，删除文件等
     * 3.关闭资源
     */
    FileSystem fs;
    @Before
    public void connect () throws URISyntaxException, IOException, InterruptedException {
        //连接HDFS方式一：
        /**
        //1.获取配置
        Configuration conf=new Configuration();
        conf.set("fs.defaultFS","hdfs://hadoop102:9820");
        //2.连接文件系统
         fs=FileSystem.get(new URI("hdfs://hadoop102:9820"),conf);
        **/
        //连接HDFS方式二：不用设置运行参数
        Configuration configuration=new Configuration();
         fs=FileSystem.get(new URI("hdfs://hadoop102:9820"),configuration,"atguigu");

    }
    @After
    public void close()throws IOException{
        fs.close();
    }

    /**
     * fs创建文件夹 [权限]
     * @throws IOException
     */
    @Test
    public void testMkdir() throws IOException {
        //3.创建文件夹
        fs.mkdirs(new Path("/java/atguigu2"));
    }

    /**
     * 文件下载
     */
    @Test
    public void  fileDownload() throws IOException {
        //new Path("/")表示下载位置在当前项目所在磁盘的根目录
        //比如：当前项目在D盘，所以/地址就是D:/
        //下载没有权限限制
        fs.copyToLocalFile(new Path("/sanguo/han/zhuge.txt"),
                new Path("/maven"));

    }

    /**
     * 文件删除[权限]
     * @throws IOException
     */
    @Test
    public void fileRemove() throws IOException {
        fs.delete(new Path("/user/atguigu/output"));
    }

    /**
     * 修改文件名字【权限】
     * @throws IOException
     */
    @Test
    public void modifyFileName() throws IOException {
        fs.rename(new Path("/user/atguigu/wcinput"),
                new Path("/user/atguigu/input"));

    }

    /**
     * 文件详情查看
     * @throws IOException
     */
    @Test
    public void  listFiles() throws IOException {
        //获取文件列表ture；表示递归
        RemoteIterator<LocatedFileStatus> listFiles =
                fs.listFiles(new Path("/user"), true);
        while (listFiles.hasNext()){
            LocatedFileStatus file = listFiles.next();
            //获取文件名字
            System.out.println(file.getPath().getName());
            //获取文件长度
            System.out.println(file.getLen());
            //获取文件权限
            System.out.println(file.getPermission());
            //分组
            System.out.println(file.getGroup());
            //获取存储块信息
            BlockLocation[] locations = file.getBlockLocations();
            for(BlockLocation bl:locations){
                //获取存储块的主机节点
                String[] hosts = bl.getHosts();
                for(String host:hosts) {
                    System.out.println(host);
                }
            }
            System.out.println("------文件分割线--------");
        }
    }

    /**
     * 判断是文件还是文件夹
     * @throws IOException
     */
    @Test
    public void testFileOrDir() throws IOException {
        FileStatus[] fileStatuses =
                fs.listStatus(new Path("/user/atguigu"));
        for (FileStatus fileStatus:fileStatuses){
            //是否为文件
            if(fileStatus.isFile()){
                System.out.println("file:"+fileStatus.getPath().getName());
            }else{
                System.out.println("dir:"+fileStatus.getPath().getName());
            }
        }
    }

    /**
     * 从本地上传文件到HDFS
     *FsDataOutputStream  :FileSystem.create return
     * @throws IOException
     */
    @Test
    public void uploadFile() throws IOException {
        FileInputStream fis=new FileInputStream("D:\\zhuge.txt");
        FSDataOutputStream fos=fs.create(new Path("/zhuge.txt"));
        IOUtils.copyBytes(fis,fos,1024,false);
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }

    /**
     * 下载文件
     * fsDataInputStream ：fileSyStem.open return
     */
    @Test
    public void downloadFile() throws IOException {
        FileOutputStream fos=new FileOutputStream("D:\\zhanghan.txt");
        FSDataInputStream fis=fs.open(new Path("/zhuge.txt"));
        IOUtils.copyBytes(fis,fos,1024,false);
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }

    /**
     * 查看切片信息
     */
    @Test
    public void trashTest() throws IOException {
        Trash trash=new Trash(fs,fs.getConf());
        trash.moveToTrash(new Path("hdfs://hadoop102:9820/log.txt"));
    }
}
