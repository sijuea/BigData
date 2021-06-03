package $02mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 20:09
 * @describe
 */
public class WordcountDriver3 {
        /**
         /**idea请求发送到集群
         方式3:步骤：
         1.添加集群配置
         2.配置先打成jar包
         3.然后job.setJar()//这个是所打jar包的位置,注掉jarxxClass
         4.在运行页面配置运行的用户名：vm options：-DHADOOP_USER_NAME=atguigu
         配置运行参数：program arguments：输入路径，输出路径
         **/
        public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
            //1.获取配置
            Configuration conf=new Configuration() ;
            conf.set("fs.defaultFS","hdfs://hadoop102:9820");
           //从idea上提交jar包到yarn集群，不加一下配置，默认在本地运行
             //指定MR运行在Yarn上
             conf.set("fs.defaultFS","hdfs://hadoop102:9820");
             conf.set("mapreduce.framework.name","yarn");
             //指定MR可以在远程集群运行
             conf.set(
             "mapreduce.app-submission.cross-platform","true");
             //指定yarn resourcemanager的位置
             conf.set("yarn.resourcemanager.hostname",
             "hadoop103");


            //开始建立job
            Job job=Job.getInstance(conf);
            //2.加载jar包
           job.setJar("D:\\ideaWorkpace\\MrDemo\\target\\MrDemo-1.0-SNAPSHOT.jar");
            //job 设置合并小文件
//            job.setInputFormatClass(CombineTextInputFormat.class);
//            CombineTextInputFormat.setMaxInputSplitSize(job,20971520);

            //3.设置map和reduce类
            job.setMapperClass(WordMap.class);
            job.setReducerClass(WordReduce.class);
            //在map和reduce阶段中间设置combiner
//        job.setCombinerClass(WordReduce.class);
            //4.设置map输出
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(IntWritable.class);
            //5.设置reduce输出【最后输出的结果key，value】
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(IntWritable.class);

            //6.设置输入和输出地址
            FileInputFormat.setInputPaths(job,new Path( args[0]));
            FileOutputFormat.setOutputPath(job,new Path( args[1]));
            //7.提交
            boolean b = job.waitForCompletion(true);
            System.exit(b?0:1);
        }

}
