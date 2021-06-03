package $02mapreduce.mapjoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author sijue
 * @date 2021/3/9 21:02
 * @describe
 */
public class OrderDriverJoin {
    /**
     * mapJoin 适用用一个小文件和一个大文件
     * 1.提前将小文件加到缓存并且处理，即mapper的setup方法
     * 2.然后再map阶段直接合并数据，不用reduce阶段
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        Configuration conf=new Configuration();
        Job job=Job.getInstance(conf);
        job.setJarByClass(OrderDriverJoin.class);
//        设置自定义分区类和reduce分区数量
//        job.setPartitionerClass(MyPartition.class);
//        job.setNumReduceTasks(2);

        job.setMapperClass(OrderMapJoin.class);
//        job.setReducerClass(OrderReduce.class);

        job.setMapOutputKeyClass(OrderBean.class);
        job.setMapOutputValueClass(NullWritable.class);

//        job.setOutputKeyClass(OrderBean.class);
//        job.setOutputValueClass(NullWritable.class);
        //job设置缓存文件:file:///D:/1/input/order/pd.txt
        job.setCacheFiles(new URI[]{new URI("file:///D:/1/input/order/pd.txt")});
        //设置reduceNum=0
        job.setNumReduceTasks(0);
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        //合并小文件操作
//      CombineTextInputFormat.setMaxInputSplitSize(job,4194304);//4M


        boolean b = job.waitForCompletion(true);
        System.out.println(b?0:1);
    }
}
