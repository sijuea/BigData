package $03hive.udtf;


import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sijue
 * @date 2021/5/11 13:52
 * @describe
 */
public class ExplodeJSONString  extends GenericUDTF {
    /**
     * 检查数据类型，返回objectinspector
     * @param argOIs ：代表函数的参数列表【不会用的时候看父类】
     * @return
     * @throws UDFArgumentException
     */
    @Override
    public StructObjectInspector initialize(StructObjectInspector argOIs) throws UDFArgumentException {
        //获取参数列表
        List<? extends StructField> allStructFieldRefs = argOIs.getAllStructFieldRefs();
        //检查参数的个数是否只有一个
        if(allStructFieldRefs.size()!=1) {
            throw new UDFArgumentException("入参数据长度错误");
        }
        //检查参数的额类型是不是string
        if(!"string".equals(allStructFieldRefs.get(0).getFieldObjectInspector().getTypeName())){
            throw new UDFArgumentException("入参类型错误");
        }

        //返回结果 1列(String) 的objectInspector
        List<String> fieldNames=new ArrayList<String>();
        fieldNames.add("actionJsonArray");
        List<ObjectInspector> fieldOIs=new ArrayList<ObjectInspector>();
        fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        return  ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames,
                fieldOIs);
    }
    Object objects []=new Object[1];
    /**
     * 处理数据，返回
     * @param args
     * @throws HiveException
     */
    @Override
    public void process(Object[] args) throws HiveException {
            String input=args[0].toString();
        JSONArray jsonArray = new JSONArray(input);
        for(int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            objects[0]=jsonObject.toString();;
            forward(objects);
        }
    }

    /**
     * 关闭资源,清理操作
     * @throws HiveException
     */
    @Override
    public void close() throws HiveException {

    }
}
