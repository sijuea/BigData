package $03hive.udaf;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFParameterInfo;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;

import java.text.DecimalFormat;
import java.util.*;
/**
 * 写的步骤
 * 函数输入的数据:一列品牌名
 *          类型：string
 * 缓冲区设计：品牌，调用优惠券的次数   map<String,Integer></>
 */

/**自定义聚合函数
 * @author sijue
 * @date 2021/5/20 15:45
 * @describe
 */
public class MyUDAF  extends AbstractGenericUDAFResolver {
        @Override
        public GenericUDAFEvaluator getEvaluator(GenericUDAFParameterInfo info) throws SemanticException {
            //UDAF函数入参所有类型的检测器
            ObjectInspector []parameterObjectInspectors = info.getParameterObjectInspectors();        //检查参数的个数
            if (parameterObjectInspectors.length != 1){
                throw new UDFArgumentException("只允许传入一个参数!");
            }
            //检查参数是否是基本数据类型 string + 8个类型
            if (parameterObjectInspectors[0].getCategory() != ObjectInspector.Category.PRIMITIVE){
                throw new UDFArgumentException("只允许传入基本数据类型!");
            }
            // 将首个参数类型转换为基本参数类型【因为只有一个参数】
            PrimitiveObjectInspector objectInspector = (PrimitiveObjectInspector) parameterObjectInspectors[0];
            //判断是否是string类型
            if ( objectInspector.getPrimitiveCategory() != PrimitiveObjectInspector.PrimitiveCategory.STRING ){
                throw new UDFArgumentException("只允许传入string类型!");
            }

            //入参类型检查
            return new myUDAFEvaluator();
        }

        @Override
        public GenericUDAFEvaluator getEvaluator(TypeInfo[] info) throws SemanticException {
            return super.getEvaluator(info);
        }

        /**
         * GenericUDAFEvaluators是Resolver内部对象
         *  Evaluator的每个方法如何实现
         *     ①所有的UDAF函数，都需要使用缓冲区累积每一行输入时，计算的临时数据
         * 流程:
         *  1.iterate所有数据到AggregationBuffer1
         *  2.merge每个缓冲区的数据 返回结果
         *  3.调用terminate 输出结果
         * 当前功能的分析：输入一列，然后输出一行
         *      输入：一个string的参数
         *      缓存区设计：map<String,Int></>
         *      函数的输出：一个string参数
         */
        public static class myUDAFEvaluator extends GenericUDAFEvaluator{
            //定义一个缓冲区
            static class MyFunc implements AggregationBuffer {

                Map<String,Integer> map=new HashMap<String,Integer>();

            };
            //标准map类型校验
            StandardMapObjectInspector mapOI;

            /**
             * 1.类型检查和类型声名【不可缺少，每个子类都会调用init，如果子类没有就调用父类的，】
             *  可以在函数运行的不同阶段，获取入参函数的参数类型
             * 2.类型检查器
             *      map,array,struct :标准的类型检查器   ObjectInspectorFactory
             *      int，double：基本类型检查器    PrimitiveObjectInspectorFactory
             * @param m 阶段，模式【枚举类】4个阶段 ，mode的每个阶段都会调用init方法，不同阶段入参不一样
             * 3.Model：
             *      (1)入参:ObjectInspector[] parameters：  传入的参数
             *          PARTIAL1 COMPLETE 原始数据类型
             *          PARTIAL2 FINAL ：参数只是部分聚合的结果，map
             *       (2)出参：
             *          PARTIAL1和 PARTIAL2  terminatePartial类型检查器 Map<String,Integer>
             *         COMPLETE、 FINAL最终值的返回类型检查器
             * 4.init：可以在函数运行的不同阶段，获取输入参数类型检测器
             * @param parameters 传入的参数
             * @return 判断mode的模式返回不同的类型选择器
             * @throws HiveException
             */
            @Override
            public ObjectInspector init(Mode m, ObjectInspector[] parameters) throws HiveException {
                //必须写
                super.init(m, parameters);
                //为MapOi赋值
                if(m==Mode.PARTIAL2||m==Mode.FINAL) {
                    // parameters 放的就是缓存区对象
                    mapOI = (StandardMapObjectInspector)parameters[0];
                }

                // //返回ObjectInspector
                if (m==Mode.PARTIAL1 || m==Mode.PARTIAL2){

                    return ObjectInspectorFactory.getStandardMapObjectInspector(
                            PrimitiveObjectInspectorFactory.javaStringObjectInspector,
                            PrimitiveObjectInspectorFactory.javaIntObjectInspector
                    );
                }else{
                    return PrimitiveObjectInspectorFactory.javaStringObjectInspector;
                }
            }

            /**
             * 返回一个新的缓冲区对象
             * @return
             * @throws HiveException
             */
            @Override
            public AggregationBuffer getNewAggregationBuffer() throws HiveException {
                return new MyFunc();
            }

            /**
             * 重置缓冲区
             * @param aggregationBuffer
             * @throws HiveException
             */
            @Override
            public void reset(AggregationBuffer aggregationBuffer) throws HiveException {
                //1.清空缓存区
                MyFunc myFunc=(MyFunc) aggregationBuffer;
                myFunc.map.clear();
            }

            /**
             * 将迭代的每一行数据添加到缓冲区
             * 类似spark的累加器中的分区内合并
             * 第一步：多个分区调用生成多个缓冲区数据
             * @param aggregationBuffer 缓冲区
             * @param objects
             * @throws HiveException
             */
            @Override
            public void iterate(AggregationBuffer aggregationBuffer, Object[] objects) throws HiveException {
                //取出自定义的UDAF中传入的参数
                String param = objects[0].toString();

                //累加到缓冲区
                MyFunc myAgg = (MyFunc) aggregationBuffer;
                Map<String, Integer> map = myAgg.map;

                // 累加时，先从缓存区取之前已经累积的结果，如果取不到，认为之前累积的值为0
                map.put(param , map.getOrDefault(param ,0) + 1);        }

            /**
             * 将缓冲区数据持久化返回，但是必须使用java的基本数据类型，包装类，list ，map，hadoopwritable
             *      不要使用自定义的类型，即使实现了序列化接口，否则会报错
             * 第二步：返回每个分区的数据
             * @param aggregationBuffer
             * @return
             * @throws HiveException
             */
            @Override
            public Object terminatePartial(AggregationBuffer aggregationBuffer) throws HiveException {
                return ((MyFunc) aggregationBuffer).map;
            }

            /**
             * 合并原本缓冲区的数和新的缓冲区数据,
             *  将o累加到aggregationBuffer
             * 第三步：合并数据 ，返回最终的缓冲区数据
             * @param aggregationBuffer  原本的缓冲区
             * @param o  新的缓冲区(调用terminatePartial 返回最新缓冲区的数据)
             * @throws HiveException
             */
            @Override
            public void merge(AggregationBuffer aggregationBuffer, Object o) throws HiveException {
                //获取要累加的缓存区的map
                MyFunc myAgg = (MyFunc) aggregationBuffer;
                Map<String, Integer> map = myAgg.map;

                // 官方推荐的标准操作   将 Object 转为  Map<String,Integer>， 通过类型检测器转
                Map<?, ?> inputMap = mapOI.getMap(o);

                PrimitiveObjectInspector keyObjectInspector = (PrimitiveObjectInspector)mapOI.getMapKeyObjectInspector();
                PrimitiveObjectInspector valueObjectInspector = (PrimitiveObjectInspector)mapOI.getMapValueObjectInspector();

                //遍历输入的map，累加值到agg
                for (Map.Entry<?, ?> entry : inputMap.entrySet()) {

                    //取出entry的key和value
                    String key = PrimitiveObjectInspectorUtils.getString(entry.getKey(), keyObjectInspector);
                    int value = PrimitiveObjectInspectorUtils.getInt(entry.getValue(), valueObjectInspector);

                    map.put(key, map.getOrDefault(key,0) + value);

                }
            }

            /**
             * 第四步：结束程序，输出最后的结果
             * @param aggregationBuffer
             * @return   将缓冲区中的数据，最终生成结果   Apple:30%,Xiaomi:20%,Huawei:30%,其他:20%'
             * @throws HiveException
             */
            @Override
            public Object terminate(AggregationBuffer aggregationBuffer) throws HiveException {
                //获取要生成结果的最终的数据
                MyFunc myAgg = (MyFunc) aggregationBuffer;
                Map<String, Integer> map = myAgg.map;
                // 计算总的用券次数
                double sum_count=0.0;
                for (Integer value : map.values()) {
                    sum_count += value;
                }
                // 按照 Map 中的value进行排序，先按照value的大小，降序排列
                List<Map.Entry<String, Integer>> entry_list = new ArrayList<>(map.entrySet());

                entry_list.sort(new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return -o1.getValue().compareTo(o2.getValue());
                    }
                });

                //  取排好序的前三  top3
                List<Map.Entry<String, Integer>> top3 = entry_list.subList(0, Math.min(3, entry_list.size()));

                //  计算top3 percent
                double top3Percent=0.0;

                // 准备一个将double转换为 百分数的格式化器
                DecimalFormat formatter = new DecimalFormat("#.##%");

                // 构建结果
                ArrayList<String> resultStrList = new ArrayList<>();


                // 构建前三的字符串
                for (Map.Entry<String, Integer> entry : top3) {

                    double per = entry.getValue() / sum_count;

                    top3Percent += per;

                    resultStrList.add(entry.getKey() + ":" + formatter.format(per));

                }

                if (entry_list.size() > 3){
                    resultStrList.add(  "其他:" + formatter.format( 1 - top3Percent));
                }

                // 将所有的字符串拼接为1个字符串
                String result = StringUtils.join(resultStrList, ',');

                return result;
            }
        }

        public static void main(String[] args) throws HiveException {
            HashMap<String, Integer> map = new HashMap<>();

            map.put("小米",20);
            map.put("华为",30);
      /*  map.put("苹果",40);
        map.put("中兴",10);*/

            myUDAFEvaluator.MyFunc myAgg = new myUDAFEvaluator.MyFunc();

            myAgg.map=map;

            Object result = new myUDAFEvaluator().terminate(myAgg);

            System.out.println(result);
        }








}
