package $02mapreduce.mapjoin;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class OrderBean implements WritableComparable<OrderBean> {
    private String id;
    private String pid;
    private String pname;
    private String amount;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public OrderBean() {
    }


    public OrderBean(String id, String pid, String pname, String amount) {
        this.id = id;
        this.pid = pid;
        this.pname = pname;
        this.amount = amount;
    }
    /*
        先按照pid排序，再按照明pname排序。
     */
    @Override
    public int compareTo(OrderBean o) {
        int i = this.pid.compareTo(o.pid);
        if (i == 0){//pid相同再按照pname排序
            return -this.pname.compareTo(o.pname);
        }
        return i;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(id);
        out.writeUTF(pid);
        out.writeUTF(pname);
        out.writeUTF(amount);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        id = in.readUTF();
        pid = in.readUTF();
        pname = in.readUTF();
        amount = in.readUTF();
    }

    @Override
    public String toString() {
        return id + " " + pname + " " + amount;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


}
