package $02mapreduce.mapjoin;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MapMapper extends Mapper<LongWritable, Text,OrderBean, NullWritable> {
    //用来存放需要缓存的数据。
    //key : pid
    //Value : pname
    private Map<String,String> map = new HashMap<>();
    /*
        将pd.txt缓存到内存中
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        FileSystem fs = null;
        BufferedReader br = null;
        FSDataInputStream fis = null;
        try {
            //1.创建流
            fs = FileSystem.get(context.getConfiguration());
            //1.1获取缓存路径
            URI[] cacheFiles = context.getCacheFiles();
            fis = fs.open(new Path(cacheFiles[0]));

            //2.读取数据 --- 一行一行的读取数据
            br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            String line = "";
            while ((line = br.readLine()) != null) {
                //切割内容
                String[] split = line.split("\t");
                //3.将数据存放到map中
                map.put(split[0], split[1]);
            }

        }catch (Exception e) {
           e.printStackTrace();
        }finally {
            //4.关资源
            if (fs != null){
                fs.close();
            }
            if (br != null){
                br.close();
            }
            if (fis != null){
                fis.close();
            }
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1.切割数据
        String[] orderInfo = value.toString().split("\t");
        //2.封装K,V
        OrderBean outKey = new OrderBean();
        outKey.setId(orderInfo[0]);
        outKey.setPid(orderInfo[1]);
        outKey.setAmount(orderInfo[2]);
        outKey.setPname(map.get(outKey.getPid()));
        //3.将数据写出去
        context.write(outKey,NullWritable.get());
    }
}
