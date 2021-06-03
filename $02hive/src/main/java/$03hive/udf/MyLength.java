package $03hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

/**
 * @author sijue
 * @date 2021/3/19 20:16
 * @describe
 */
public class MyLength  extends GenericUDF {
    /**
     * 初始化方法,里面要做三件事
     * 1.约束函数传入参数的个数
     * 2.约束函数传入参数的类型
     * 3.约束函数返回值的类型
     * @param arguments  函数传入参数的类型
     * @return
     * @throws UDFArgumentException
     */
    @Override
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if(arguments.length!=1){
            throw new UDFArgumentLengthException("argu num error");
        }
        if (!arguments[0].getCategory().equals(ObjectInspector.Category.PRIMITIVE)){
            throw new UDFArgumentTypeException(0,"category error");
        }
        return PrimitiveObjectInspectorFactory.javaIntObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        Object o = arguments[0].get();
        int length=o.toString().length();

        return length;
    }

    @Override
    public String getDisplayString(String[] children) {
        return "";
    }
}
