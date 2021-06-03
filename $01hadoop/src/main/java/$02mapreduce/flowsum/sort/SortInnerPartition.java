package $02mapreduce.flowsum.sort;

import $02mapreduce.flowsum.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author sijue
 * @date 2021/3/9 14:49
 * @describe
 */
public class SortInnerPartition  extends Partitioner<FlowBean, Text> {
    @Override
    public int getPartition(FlowBean flowBean, Text text, int i) {
        String str=text.toString();
        String substring = str.substring(0, 3);
        switch (substring) {
            case "136":
                i = 0;
                break;
            case "137":
                i = 1;
                break;
            case "138":
                i = 2;
                break;
            case "139":
                i = 3;
                break;
            default:
                i=4;
        }
        return i;
    }
}
