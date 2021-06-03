package $02mapreduce.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/5 20:49
 * @describe
 */
public class WordMap extends Mapper<LongWritable, Text,Text,IntWritable> {
    IntWritable intWritable=new IntWritable(1);
    Text text=new Text();
    /**
     *  map阶段默认一行一行读取数据
     * @param key 行的索引,是long型
     * @param value 一行数据
     * @param context 输出的结果集
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.获取一行数据
        String string = value.toString();
        //2.根据空格拆分一行数据
        String[] s = string.split(" ");
        for(String s1:s) {
            text.set(s1);
            context.write(text,intWritable);
        }

    }
}
