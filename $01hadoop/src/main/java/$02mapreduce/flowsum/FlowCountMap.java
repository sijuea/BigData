package $02mapreduce.flowsum;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/8 9:37
 * @describe
 */
public class FlowCountMap  extends Mapper<LongWritable,Text,Text,FlowBean> {
    FlowBean flowBean=new FlowBean();
    Text text=new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //获取当前一行数据的切片信息
        FileSplit inputSplit =(FileSplit) context.getInputSplit();
        System.out.println("==================切片长度:"+inputSplit.getLength());
        System.out.println("=================切片名字:"+inputSplit.getPath().getName());
        String line=value.toString();
        String[] s = line.split("\t");
        for(int i=0;i<s.length;i++){
            String phone=s[1];
            Long upFlow=Long.valueOf(s[s.length-3]);
            Long downFlow=Long.valueOf(s[s.length-2]);
            flowBean.setUpFlow(upFlow);
            flowBean.setDownFlow(downFlow);
            flowBean.setSumFlow(upFlow+downFlow);
            text.set(phone);
        }
        context.write(text,flowBean);


    }
}
