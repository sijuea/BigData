package $02mapreduce.flowsum.sort;

import $02mapreduce.flowsum.FlowCountDriver;
import $02mapreduce.flowsum.FlowBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 14:08
 * @describe
 */
public class SortMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf=new Configuration();
        conf.set("fs.defaultFS","hdfs://hadoop102:9820");
        conf.set("hadoop.http.staticuser.user","atguigu");
        Job job=Job.getInstance(conf);
        job.setJarByClass(FlowCountDriver.class);

        //设置分区类和分区数量
        job.setPartitionerClass(SortInnerPartition.class);
        job.setNumReduceTasks(5);
        job.setMapperClass(SortMap.class);
        job.setReducerClass(SortReduce.class);

        job.setMapOutputKeyClass(FlowBean.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(FlowBean.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //合并小文件操作
//        CombineFileInputFormat.setMaxInputSplitSize(job,4194304);//4M

        boolean b = job.waitForCompletion(true);
        System.out.println(b?0:1);
    }
}
