package $02mapreduce.flowsum;




import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author sijue
 * @date 2021/3/8 9:37
 * @describe
 */
public class FlowBean  implements  WritableComparable<FlowBean> {
   private Long  upFlow;
   private Long downFlow;
   private Long sumFlow;
   private String phone;
    public FlowBean() {
    }

    public FlowBean(Long upFlow, Long downFlow, Long sumFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = sumFlow;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(Long upFlow) {
        this.upFlow = upFlow;
    }

    public Long getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(Long downFlow) {
        this.downFlow = downFlow;
    }

    public Long getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(Long sumFlow) {
        this.sumFlow = sumFlow;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        upFlow=in.readLong();
        downFlow=in.readLong();
        sumFlow=in.readLong();
    }

    @Override
    public String toString() {
        return  upFlow +
                "\t" + downFlow +
                "\t" + sumFlow ;
    }

    @Override
    public int compareTo(FlowBean o) {
        if(o.getSumFlow()>this.sumFlow){
            return 1;
        }else if( o.getSumFlow()<this.sumFlow){
            return -1;
        }else {
            return 0;
        }
    }
}
