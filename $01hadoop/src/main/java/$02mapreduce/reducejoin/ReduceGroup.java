package $02mapreduce.reducejoin;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/*
    自定义分组:
    1.自定义一个类并继承WritableComparator
    2.创建一个空参构造器并调用父类有参构造器
    3.重写public int compare(WritableComparable a, WritableComparable b) 方法
            在该方法中实现分组的方式。
    4.注意：如果不指定分组方式默认和排序方式相同。
 */
public class ReduceGroup extends WritableComparator {

    public ReduceGroup(){
        /*
        WritableComparator(Class<? extends WritableComparable> keyClass,
                boolean createInstances)
            keyClass : key的类型的类的运行时类的对象（key.class）
            createInstances : 是否创建实例
         */
        //调用父类的构造器
        super(OrderBean.class,true);
    }


    /*
        指定分组方式（如果不指定分组方式那么默认的分组方式和排序方式相同）：
        作用 ：只按照pid分组
     */
    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        if (a instanceof OrderBean && b instanceof OrderBean){
            OrderBean o1 = (OrderBean) a;
            OrderBean o2 = (OrderBean) b;
            return o1.getPid().compareTo(o2.getPid());
        }
        return 0;
    }
}
