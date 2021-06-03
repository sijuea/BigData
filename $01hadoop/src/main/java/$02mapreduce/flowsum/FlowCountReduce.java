package $02mapreduce.flowsum;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/8 9:37
 * @describe
 */
public class FlowCountReduce  extends Reducer<Text,FlowBean,Text,FlowBean> {
    FlowBean flowBean=new FlowBean();
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        Long upFlowSum = 0L;
        Long downFlowSum=0L;
        Long sum=0L;
        for(FlowBean flow:values){
            upFlowSum+=flow.getUpFlow();
            downFlowSum+=flow.getDownFlow();
            sum+=flow.getSumFlow();
        }
        flowBean.setUpFlow(upFlowSum);
        flowBean.setDownFlow(downFlowSum);
        flowBean.setSumFlow(sum);
        context.write(key,flowBean);
    }
}
