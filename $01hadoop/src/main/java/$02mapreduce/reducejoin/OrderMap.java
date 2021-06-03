package $02mapreduce.reducejoin;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 21:01
 * @describe
 */
public class OrderMap  extends Mapper<LongWritable, Text,OrderBean, NullWritable> {

    String filename;
    OrderBean orderBean=new OrderBean();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        FileSplit fs=(FileSplit) context.getInputSplit();
        filename=fs.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line=value.toString();
        String[] split = line.split("\t");
        if(filename.endsWith("pd.txt")){
            orderBean.setPid(split[0]);
            orderBean.setPname(split[1]);
            orderBean.setId("");
            orderBean.setNum(0);
        }else{
           orderBean.setId(split[0]);
           orderBean.setPid(split[1]);
           orderBean.setNum(Integer.valueOf(split[2]));
           orderBean.setPname("");
        }
        context.write(orderBean,NullWritable.get());

    }
}
