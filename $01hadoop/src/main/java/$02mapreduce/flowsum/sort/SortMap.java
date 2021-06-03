package $02mapreduce.flowsum.sort;

import $02mapreduce.flowsum.FlowBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 14:08
 * @describe
 */
public class SortMap extends Mapper<LongWritable, Text, FlowBean,Text> {
    Text v=new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String text=value.toString();
        String[] s = text.split("\t");
        String phone=s[0];
        FlowBean flowBean=new FlowBean(Long.valueOf(s[1]),
                Long.valueOf(s[2]),Long.valueOf(s[3]));
        v.set(phone);
        context.write(flowBean,v);
    }
}
