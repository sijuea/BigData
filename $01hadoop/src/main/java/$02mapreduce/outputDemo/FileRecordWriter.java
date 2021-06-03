package $02mapreduce.outputDemo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/9 16:00
 * @describe
 */
public class FileRecordWriter extends RecordWriter<Text, NullWritable> {
    private FSDataOutputStream atguigu;
    private FSDataOutputStream other;

    /**
     * 方式一
     * @param job
     */
    /**
    public FileRecordWriter(TaskAttemptContext job) {
        Configuration configuration = job.getConfiguration();
        try {
            FileSystem fs=FileSystem.get(configuration);
            //输出流创建
            Path p0=new Path("/log/atguigu.log");
            Path p1=new Path("/log/other.log");
             atguigu = fs.create(p0);
             other = fs.create(p1);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    */
    /**
     * 方式er
    */
    public FileRecordWriter(TaskAttemptContext job){
        try {
            FileSystem fs=FileSystem.get(job.getConfiguration());
            atguigu=fs.create(new Path(FileOutputFormat.getOutputPath(job)+"/atguigu.log"));
            other=fs.create(new Path(FileOutputFormat.getOutputPath(job)+"/other.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void write(Text text, NullWritable nullWritable) throws IOException, InterruptedException {
        String url=text.toString();
        if (url.contains("atguigu")){
            atguigu.write(url.getBytes());
        }else{
            other.write(url.getBytes());
        }
    }

    @Override
    public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        IOUtils.closeStream(atguigu);
        IOUtils.closeStream(other);
    }
}
