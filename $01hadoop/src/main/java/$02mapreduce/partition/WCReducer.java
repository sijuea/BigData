package $02mapreduce.partition;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
    Reduce阶段ReduceTask要做的事情

    Reducer<KEYIN, VALUEIN, KEYOUT, VALUEOUT>
        四个泛型分两组
            第一组：
                KEYIN ：Mapper输出的key的类型
                VALUEIN：Mapper输出的value的类型

             第二组：
                KEYOUT ：Reducer输出的key（单词）
                VALUEOUT ：Reducer输出的value（单词的数量）
 */
public class WCReducer extends Reducer<Text, IntWritable,Text,IntWritable> {
    //写出的value
    private IntWritable outvalue = new IntWritable();
    /**
     * 该方法用来实现ReduceTask所需要做的功能
     * reduce方法被循环调用。一次读一组数据（相同的key为一组）
     * @param key 读进来的key（单词）
     * @param values 读进来的所有的value
     * @param context 上下文（在这用来写出K,V）
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        //1.遍历所有的value
        for (IntWritable value : values) {
            //将IntWritable转化成int类型
            int v = value.get();
            sum += v;
        }
        //2.封装K,V
        outvalue.set(sum);
        //3.写出K,V
        context.write(key,outvalue);

    }
}
