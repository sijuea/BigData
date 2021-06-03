package $02mapreduce.outputDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 15:40
 * @describe
 */
public class LogDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf=new Configuration();
//        conf.set("fs.defaultFS","hdfs://hadoop102:9820");
        Job job=Job.getInstance(conf);
        job.setJarByClass(LogDriver.class);
//        设置自定义分区类和reduce分区数量
//        job.setPartitionerClass(MyPartition.class);
//        job.setNumReduceTasks(2);
        job.setMapperClass(LogMap.class);
        job.setReducerClass(LogReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);
        job.setOutputFormatClass(MyOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //合并小文件操作
//      CombineTextInputFormat.setMaxInputSplitSize(job,4194304);//4M


        boolean b = job.waitForCompletion(true);
        System.out.println(b?0:1);
    }
}
