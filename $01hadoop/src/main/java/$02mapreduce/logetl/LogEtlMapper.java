package $02mapreduce.logetl;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/10 20:30
 * @describe
 */
public class LogEtlMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        boolean b = parseLog(value, context);
        if (!b) {
            return;
        }
        context.write(value, NullWritable.get());
    }

    public boolean parseLog(Text text, Context context) {
        String line = text.toString();
        String[] s = line.split(" ");
        if (s.length > 11) {
            context.getCounter("rightLog", "true").increment(1);
            return true;
        } else {
            context.getCounter("errorLog", "false").increment(1);
            return false;
        }
    }
}
