package $02mapreduce.flowsum.partition;

import $02mapreduce.flowsum.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author sijue
 * @date 2021/3/9 10:31
 * @describe
 */
public class MyPartition extends Partitioner<Text, FlowBean> {
    @Override
    public int getPartition(Text text, FlowBean flowBean, int i) {
        String str=text.toString();
        String regex="^13[6789]\\d{8}$";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(str);
        if(m.matches()){
            return 0;
        }
        else{
            return 1;
        }
    }

}
