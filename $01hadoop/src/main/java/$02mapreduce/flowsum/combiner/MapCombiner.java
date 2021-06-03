package $02mapreduce.flowsum.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 15:05
 * @describe  和字母计数的mr一起使用
 */
public class MapCombiner extends Reducer<Text, IntWritable,Text, IntWritable> {
    IntWritable v=new IntWritable();
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum=0;
        for(IntWritable i:values){
            sum+=i.get();
        }
        v.set(sum);
        context.write(key,v);
    }
}
