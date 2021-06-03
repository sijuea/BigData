package $02mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/5 20:49
 * @describe
 */
public class WordReduce extends Reducer<Text, IntWritable,Text,IntWritable> {
    IntWritable intWritable =new IntWritable();
    /**
     * 将map阶段数据汇总输出
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum=0;//用于计数
        for(IntWritable intWritable:values){
           sum=sum+intWritable.get();
       }
        intWritable.set(sum);
        context.write(key,intWritable);
    }
}
