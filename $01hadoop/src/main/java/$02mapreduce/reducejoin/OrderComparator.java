package $02mapreduce.reducejoin;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author sijue
 * @date 2021/3/13 10:13
 * @describe
 */
public class OrderComparator extends WritableComparator {
    public OrderComparator() {
        super(OrderBean.class,true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        OrderBean o1=null;
        OrderBean o2=null;
        if(a instanceof OrderBean && b instanceof OrderBean){
             o1=(OrderBean) a;
             o2=(OrderBean) b;

        }
        return o1.getPid().compareTo(o2.getPid());
    }
}
