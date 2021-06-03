package $01hdfs;

/*
    操作HDFS：
    1.创建文件系统对象
    2.具体操作 ：上传，删除，下载.....
    3.关资源
 */

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

public class HDFSDemo {

    private FileSystem fs;
    /*
        在执行单元测试方法前执行的方法
        创建对象
     */
    @Before
    public void before() throws Exception {
         /*
        get(final URI uri, final Configuration conf,
        final String user)
        uri : HDFS的地址
        conf : 需要使用的配置信息
        user : 用来操作HDFS的用户
         */
        // uri : HDFS的地址
        URI uri = new URI("hdfs://hadoop102:9820");
        //conf : 需要使用的配置信息
        Configuration conf = new Configuration();
        //user : 用来操作HDFS的用户
        String user = "atguigu";
        fs = FileSystem.get(uri, conf, user);
    }

    /*
        在执行单元测试方法后执行的方法
        关闭资源
     */
    @After
    public void after(){
        //3.关资源
        try {
            if (fs != null) {
                fs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //2.具体的操作
    //上传
    @Test
    public void test() throws IOException {
        /*
        copyFromLocalFile(boolean delSrc, boolean overwrite,
                                Path src, Path dst)
          delSrc : 是否删除源文件（是剪切还是复制）
          overwrite : 如果目标文件已存在是否覆盖目标文件
                注意：如果不覆盖但目标文件又存在则报错
          src : 源文件路径（本地）
          dst : 目标文件路径（HDFS）
         */
        fs.copyFromLocalFile(true,
                true,new Path("D:\\io\\hdfs\\aa.txt"),new Path("/hdfs"));
    }

    /*
        下载
     */
    @Test
    public void test2() throws IOException {
        /*
            copyToLocalFile(boolean delSrc, Path src, Path dst,
      boolean useRawLocalFileSystem)
        delSrc : 是否删除源文件（HDFS上的文件）
        src : 源文件路径（HDFS）
        dst : 目标文件路径（本地）
        useRawLocalFileSystem ： 是否使用useRawLocalFileSystem
                如果使用：不会下载crc校验文件
                如果不使用 ： 会下载crc校验文件
         */
        fs.copyToLocalFile(false,new Path("/hdfs/aa.txt"),new Path("D:\\io\\hdfs"),
                false);

    }

    /*
       删除
     */
    @Test
    public void test3() throws IOException {
        /*
            delete(Path f, boolean recursive)
            f : 删除的数据的路径
            recursive : 是否递归（如果是目录（非空）必须是true否则会报错。如果是文件true和false都可以）
         */
        fs.delete(new Path("/longge/sanguo.txt"),true);
    }

    /*
        改名
     */
    @Test
    public void test4() throws IOException {
        /*
            rename(Path src, Path dst)
            src : 源文件路径
            dst : 目标文件路径
         */
        //fs.rename(new Path("/longge/xiyou.txt"),new Path("/longge/xiyouji.txt"));

        //移动
        fs.rename(new Path("/longge/xiyouji.txt"),new Path("/hdfs/xiyouji.txt"));
    }

    /*
        文件详情查看
     */
    @Test
    public void test5() throws IOException {
        /*
            listFiles(
                    final Path f, final boolean recursive)
            f : 目标路径
            recursive ： 是否递归
         */
        RemoteIterator<LocatedFileStatus> remoteIterator = fs.listFiles(new Path("/"), true);

        while(remoteIterator.hasNext()){
            //文件详情对象
            LocatedFileStatus fileStatus = remoteIterator.next();
            //文件名
            System.out.println("=============" + fileStatus.getPath().getName() + "===================");
            System.out.println("=====所属主:" + fileStatus.getOwner());
            System.out.println("=====副本数量：" + fileStatus.getReplication());
            //获取块的信息
            BlockLocation[] blockLocations = fileStatus.getBlockLocations();
            for (BlockLocation block : blockLocations) {
                System.out.println(block);
            }
        }
    }

    /*
        判断是文件还是目录
     */
    @Test
    public void test6() throws IOException {
        /*
            参数 ： 目标路径
         */
        FileStatus[] fileStatus = fs.listStatus(new Path("/"));

        for (FileStatus status : fileStatus) {
            //输出文件名
            System.out.println("====" + status.getPath().getName() + "====");
            if (status.isDirectory()){
                System.out.println("是一个目录");
            }else if (status.isFile()){
                System.out.println("是一个文件");
            }
        }
    }

    /*
        用流的方式实现HDFS上传和下载内容

        上传
     */
    @Test
    public void test7() throws IOException {
        //读-从本地读（文件输入流）
        FileInputStream fis = new FileInputStream("D:\\io\\hdfs\\aa.txt");
        //写-向HDFS写
        FSDataOutputStream fos = fs.create(new Path("/hdfs/aa.txt"));
        //一边读一边写
        /*
            文件对拷
            copyBytes(InputStream in, OutputStream out,
                               int buffSize, boolean close)
             in : 输入流
             out : 输出流
             buffsize ：缓存大小
             close : 是否关流
         */
        IOUtils.copyBytes(fis,fos,1024,false);
        //关流
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }

    /*
        用流的方式实现HDFS上传和下载内容

        下载
     */
    @Test
    public void test8() throws IOException {
        //读 - 从HDFS上读
        FSDataInputStream fis = fs.open(new Path("/hdfs/xiyouji.txt"));
        //写 - 向本地写(文件输出流)
        FileOutputStream fos = new FileOutputStream("D:\\io\\hdfs\\xiyouji.txt");
        //文件对拷
        IOUtils.copyBytes(fis,fos,1024,true);
    }
}











