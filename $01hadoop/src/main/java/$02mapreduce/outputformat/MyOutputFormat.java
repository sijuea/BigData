package $02mapreduce.outputformat;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/*
    自定义的OuputFormat

    1. FileOutputFormat<K, V>
            K: 如果前面是Reducer那就是Reducer的Key，如果前面是Mapper那就是Mapper的key，
                如果前面是InputFormat那就是InputFormat的key
            V:如果前面是Reducer那就是Reducer的Value，如果前面是Mapper那就是Mapper的Value，
                如果前面是InputFormat那就是InputFormat的Value
 */
public class MyOutputFormat extends FileOutputFormat<LongWritable, Text> {
    /*
        返回RecordWriter对象
     */
    @Override
    public RecordWriter<LongWritable, Text> getRecordWriter(TaskAttemptContext job) throws IOException, InterruptedException {
        return new MyRecordWriter(job);
    }
}
