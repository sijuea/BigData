package $02mapreduce.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

/*
    程序的入口：创建Job对象并配置
 */
public class WCDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        //创建Job对象
        Job job = Job.getInstance(conf);

        //设置输出时使用SequenceFileOutputFormat
        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        //设置Jar加载路径---本地模式不设置也没问题
        job.setJarByClass(WCDriver.class);
        //设置Mapper和Reducer类
        job.setMapperClass(WCMapper.class);
        job.setReducerClass(WCReducer.class);
        //设置Mapper输出的k,v类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //设置最终输出的k,v类型（在这是Reducer输出的k,v类型）
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        //设置数据源读出和输出的路径
        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output"));
        //执行job
        /*
            参数 ：是否打印进度
            返回值 ：如果job执行成功返回true
         */
        boolean b = job.waitForCompletion(true);
    }
}
