package $02mapreduce.partition2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class FlowReducer extends Reducer<Text, FlowBean,Text, FlowBean> {

    /*
        1233333333  100 20  120
        1233333333  200 30  230
     */
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        int sumUpFlow = 0;//上行总流量
        int sumDownFlow = 0;//下行总流量
        //1.遍历同一个手机号的所有value
        for (FlowBean value: values ) {
            sumUpFlow += value.getUpFlow();
            sumDownFlow += value.getDownFlow();
        }
        //2.封装K,V
        FlowBean outValue = new FlowBean(sumUpFlow, sumDownFlow);
        //3.写出K,V
        context.write(key,outValue);
    }
}
