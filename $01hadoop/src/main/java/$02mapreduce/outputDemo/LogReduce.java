package $02mapreduce.outputDemo;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 15:40
 * @describe
 */
public class LogReduce  extends Reducer<Text, NullWritable, Text, NullWritable> {
    Text k=new Text();
    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
           String str=key.toString();
           str=str+"\r\n";
           k.set(str);
           context.write(k,NullWritable.get());
    }
}
