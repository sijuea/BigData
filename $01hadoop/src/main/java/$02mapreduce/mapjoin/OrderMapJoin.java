package $02mapreduce.mapjoin;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sijue
 * @date 2021/3/9 21:01
 * @describe
 */
public class OrderMapJoin extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

    OrderBean orderBean=new OrderBean();
    Map<String,String> map=new HashMap<>();
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        URI[] cacheFiles = context.getCacheFiles();
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream fis = fs.open(new Path(cacheFiles[0]));
        BufferedReader bf=new BufferedReader(new InputStreamReader(fis,"utf-8"));
        String read;
        while (StringUtils.isNotEmpty(read=bf.readLine())) {
            String[] split = read.split("\t");
            map.put(split[0], split[1]);
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line=value.toString();
        String[] split = line.split("\t");
           orderBean.setId(split[0]);
           orderBean.setPid(split[1]);
           orderBean.setNum(Integer.valueOf(split[2]));
           orderBean.setPname(map.get(split[1]));
        context.write(orderBean,NullWritable.get());
    }
}
