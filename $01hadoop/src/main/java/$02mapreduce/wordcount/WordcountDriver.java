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
 * @date 2021/3/5 20:49
 * @describe
 * 运行程序的方式：以wordcount为例
 * 1.idea使用本地的hadoop环境运行
 * 2.idea打成jar包在hadoop集群使用命令执行
 * 3.idea配置集群信息，已经相关配置，从本地提交job到yarn集群，在yarn集群运行
 * 4.连接集群hdfs，读取hdfs文件在本地运行
 */
public class WordcountDriver {
    /**
     * 在本地环境运行,input和output都是本地环境
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.获取配置
        Configuration conf=new Configuration() ;
        //开始建立job
        Job job=Job.getInstance(conf);
        //2.设置jar包的主类或者加载jar包
        job.setJarByClass(WordcountDriver.class);
        //job 设置合并小文件
//        job.setInputFormatClass(CombineTextInputFormat.class);
//        CombineTextInputFormat.setMaxInputSplitSize(job,20971520);

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
