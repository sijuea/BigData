package $02mapreduce.writable2;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class FlowDriver {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //1.创建Job对象
        Job job = Job.getInstance(new Configuration());

        //2.给Job对象赋值
        //2.1设置Jar加载路径---本地模式不设置也没问题
        job.setJarByClass(FlowDriver.class);
        //2.2设置Mapper和Reducer类
        job.setMapperClass(FlowMapper.class);
        job.setReducerClass(FlowReducer.class);
        //2.3设置Mapper输出的k,v类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(FlowBean.class);
        //2.4设置最终输出的k,v类型（在这是Reducer输出的k,v类型）
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBean.class);
        //2.5设置数据的输入和输出路径
        FileInputFormat.setInputPaths(job,new Path("D:\\io\\input2"));
        FileOutputFormat.setOutputPath(job,new Path("D:\\io\\output22"));

        //3.执行Job
        job.waitForCompletion(true);
    }
}
