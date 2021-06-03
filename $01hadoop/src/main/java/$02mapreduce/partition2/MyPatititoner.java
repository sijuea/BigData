package $02mapreduce.partition2;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/*
    自定义分区类

    Partitioner<KEY, VALUE>

    KEY : map输出的key的类型
    VALUE:map输出的value的类型
 */
public class MyPatititoner extends Partitioner<Text,FlowBean> {
    /**
     * 需求：
     *  手机号136、137、138、139开头都分别放到一个独立的4个文件中，其他开头的放到一个文件中。
     *
     * 获取分取号
     * @param text  map输出的key
     * @param flowBean map输出的value
     * @param numPartitions reduceTask的数量
     * @return
     */
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        String phoneNumber = text.toString();
        //判断手机号
        if (phoneNumber.startsWith("136")){
            return 0;
        }else if (phoneNumber.startsWith("137")){
            return 1;
        }else if (phoneNumber.startsWith("138")){
            return 2;
        }else if (phoneNumber.startsWith("139")){
            return 3;
        }else{
            return 4;
        }
    }
}
