package $02mapreduce.comparable2;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
     注意：因为要对总流量进行排序所以FlowBean要做为key,手机号做为value
 */
public class FlowMapper extends Mapper<LongWritable,Text, FlowBean, Text> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //将Text转成String
        String line = value.toString();
        //将数据切割
        String[] phoneInfo = line.split("\t");
        //封装K,V
        FlowBean outkey = new FlowBean(Long.parseLong(phoneInfo[phoneInfo.length-3]),
                Long.parseLong(phoneInfo[phoneInfo.length-2]));
        Text outvalue = new Text(phoneInfo[1]);
        //写出k,v
        context.write(outkey,outvalue);
    }
}
