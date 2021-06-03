package $02mapreduce.etl;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class ETLMapper extends Mapper<LongWritable,Text, Text, NullWritable> {

    private  Counter fail;
    private  Counter success;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //创建计算器对象
        /*
        getCounter(String groupName, String counterName)
        groupName : 组名（随便写）
        counterName ：计数器的名字（随便写）

         */
         fail = context.getCounter("ETL", "fail");
         success = context.getCounter("ETL", "success");
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] lineLength = value.toString().split(" ");

        if (lineLength.length > 11){
            //写出去
            context.write(value,NullWritable.get());
            success.increment(1);//增加1
        }else{
            fail.increment(1);//增加1
        }
    }
}
