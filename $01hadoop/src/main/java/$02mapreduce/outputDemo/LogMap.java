package $02mapreduce.outputDemo;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 15:40
 * @describe
 */
public class LogMap extends Mapper<LongWritable, Text,Text, NullWritable> {
    Text v=new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        context.write(value,NullWritable.get());
    }
}
