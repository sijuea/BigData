package $02mapreduce.flowsum;
import $02mapreduce.flowsum.partition.MyPartition;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


/**
 * @author sijue
 * @date 2021/3/8 9:37
 * @describe
 */
public class FlowCountDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf=new Configuration();
        conf.set("fs.defaultFS","hdfs://hadoop102:9820");
        conf.set("hadoop.http.staticuser.user","atguigu");
        Job job=Job.getInstance(conf);
        job.setJarByClass(FlowCountDriver.class);
//        设置自定义分区类和reduce分区数量
        job.setPartitionerClass(MyPartition.class);
        job.setNumReduceTasks(2);

        job.setMapperClass(FlowCountMap.class);
        job.setReducerClass(FlowCountReduce.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //合并小文件操作
//        CombineTextInputFormat.setMaxInputSplitSize(job,4194304);//4M


        boolean b = job.waitForCompletion(true);
        System.out.println(b?0:1);

    }

}
