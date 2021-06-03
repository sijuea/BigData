package $02mapreduce.reducejoin;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author sijue
 * @date 2021/3/9 21:02
 * @describe
 */
public class OrderReduce extends Reducer<OrderBean, NullWritable,OrderBean, NullWritable> {
    String pname=null;
    /**
     *
     * @param key NUllWritable
     * @param values 多个orderBean，第一个key有pname，因为第一个读的是pd.txt
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        Iterator<NullWritable> iterator = values.iterator();
//        context.getCurrentKey()
        if(StringUtils.isNotBlank(key.getPname())) {
            iterator.next();
            pname = key.getPname();
        }
        while (iterator.hasNext()){
            iterator.next();
            key.setPname(pname);
            context.write(key,NullWritable.get());
        }
    }
}
