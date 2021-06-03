package $02mapreduce.writable;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/*
    自定义类实现序列化：
    1.自定一个类必实现Writable接口
    2.重写write和readFields方法
    3.在readFields方法中反序列化时的顺序和序列化时的顺序必须保持一致。
 */
public class FlowBean implements Writable {
    private int upFlow;
    private int downFlow;
    private int sumFlow;

    public FlowBean(){

    }

    /*
        序列化时调用的方法
     */
    public void write(DataOutput out) throws IOException {
        //顺序无所谓
        out.writeInt(upFlow);
        out.writeInt(downFlow);
        out.writeInt(sumFlow);
    }
    /*
        反序列化时调用的方法
     */
    public void readFields(DataInput in) throws IOException {
        //注意：反序列化时的顺序必须和序列化时的顺序保持一致
        upFlow = in.readInt();
        downFlow = in.readInt();
        sumFlow = in.readInt();
    }

    public FlowBean(int upFlow, int downFlow, int sumFlow) {
        this.upFlow = upFlow;
        this.downFlow = downFlow;
        this.sumFlow = sumFlow;
    }

    public int getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(int upFlow) {
        this.upFlow = upFlow;
    }

    public int getDownFlow() {
        return downFlow;
    }

    public void setDownFlow(int downFlow) {
        this.downFlow = downFlow;
    }

    public int getSumFlow() {
        return sumFlow;
    }

    public void setSumFlow(int sumFlow) {
        this.sumFlow = sumFlow;
    }


}
