package $02mapreduce.reducejoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 21:02
 * @describe
 */
public class OrderDriver {
    /**
     * reduceJoin 就是map读取两张表的数据，在reduce阶段进行合并
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf);
        job.setJarByClass(OrderDriver.class);


        job.setMapperClass(OrderMap.class);
        job.setReducerClass(OrderReduce.class);
        //设置分组
        job.setGroupingComparatorClass(OrderComparator.class);
        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);

        job.setOutputKeyClass(OrderBean.class);
        job.setOutputValueClass(NullWritable.class);
//        job.setOutputFormatClass(MyOutputFormat.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //合并小文件操作
//      CombineTextInputFormat.setMaxInputSplitSize(job,4194304);//4M


        boolean b = job.waitForCompletion(true);
        System.out.println(b?0:1);
    }
}
