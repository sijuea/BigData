package $02mapreduce.flowsum.sort;

import $02mapreduce.flowsum.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 14:08
 * @describe
 */
public class SortReduce extends Reducer<FlowBean,Text,FlowBean, Text> {
    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //不同的手机号可能存在相同的总流量数据
        for(Text text:values){
            context.write(key,text);
        }
    }
}
