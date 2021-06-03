package com.demo;

import com.demo.entity.Employee;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.*;
import io.searchbox.core.search.aggregation.AvgAggregation;
import io.searchbox.core.search.aggregation.MetricAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author sijue
 * @date 2021/6/1 9:09
 * @describe
 *  Jest 使用工厂建立客户端对象： JestClientFactory
 *      调用返回客户端对象的executor执行：传参类型是Action
 *          Action常用类型：
 *              读：Search
 *              写：删除：delete
 *                  新增/修改：Index
 *               批量操作：Bulk
 *  Jest大量使用建造者模式
 *      普通：A a=new A()
 *      建造者 A a=new ABuilder().setxxx().setXXX().build()
 */
public class EsDemo {
    JestClient jestClient;
    @Before
    public void  init(){
        //1.创建ES客户端构建器
        JestClientFactory factory=new JestClientFactory();
        //2.创建ES客户端连接地址
        HttpClientConfig httpClientConfig = new HttpClientConfig.Builder("http://hadoop102:9200").build();

        //3.设置ES连接地址
        factory.setHttpClientConfig(httpClientConfig);

        //4.获取ES客户端连接【使用工厂返回一个客户端实例】
         jestClient = factory.getObject();
    }

    /**方式一：
     * 插入单条数据，使用json字符串
     * @throws IOException
     * kibana查询语句：GET /test/emps/1035
     */
    @Test
    public void insertByOne1() throws IOException {
        String date=
                "{\"empid\":1001,\"age\":20,\"balance\":2000," +
                        "\"name\":\"李三\",\"gender\":\"男\",\"hobby\":\"吃饭睡觉\"}";
        Index build = new Index.Builder(date)
                .type("emps")
                .index("test")
                .id("1035").build();
        //执行sql【异步执行】
        DocumentResult execute1 = jestClient.execute(build);
        System.out.println(execute1.getResponseCode());

    }


    /**方式二：
     * 使用对象封装之后写入
     * 对于有多个属性的数据，可以使用样例类进行封装然后插入
     * 写完之后查询的时候使用GET /indexName/typeName/id查询
     * @throws IOException
     */
    @Test
    public void insertByOne2() throws IOException {
        Employee employee=new Employee("1003",30,3000,"lisu","nan","睡觉");
        Index build2 = new Index.Builder(employee)
                .type("emps")
                .index("test")
                .id("1036").build();
        DocumentResult execute2 = jestClient.execute(build2);
        //一般返回码以2开头的都是运行成功的
        System.out.println(execute2.getResponseCode());
    }
    /**
     * 批量操作【可以包含不同类型的操作，比如可以同时包含删除和增加】
     *  先将批量操作的每个操作封装为action
     *  然后将action添加到bulk
     * @throws IOException
     */
    @Test
    public void  insertBatch() throws IOException {
        //删除1036、1035  Delete
        Delete build1 = new Delete.Builder("1035").index("test").type("emps").build();
        Delete build2 = new Delete.Builder("1036").index("test").type("emps").build();
        //  插入1037  Index
        Employee employee=new Employee("1030",30,3000,"司马翠花","nan","睡觉");
        Index index = new Index.Builder(employee)
                .type("emps")
                .index("test")
                .id("1037").build();
        Bulk build = new Bulk.Builder()
                .addAction(build1)
                .addAction(build2)
                .addAction(index).build();
        jestClient.execute(build);
    }

    /**
     * 使用search封装string类型的查询语句查询
     * 案例：读取聚合查询之后的操作
     *
     */
    @Test
    public void read1() throws IOException {
        String json=
                "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"hobby\": \"购物\"\n" +
                "    }\n" +
                "  }, \n" +
                "   \"aggs\": {\n" +
                "     \"emps_count\": {\n" +
                "       \"terms\": {\n" +
                "         \"field\": \"gender.keyword\",\n" +
                "         \"size\": 10\n" +
                "       },\n" +
                "       \"aggs\": {\n" +
                "         \"gender_avg_age\": {\n" +
                "           \"avg\": {\n" +
                "             \"field\": \"age\"\n" +
                "           }\n" +
                "         }\n" +
                "       }\n" +
                "     }\n" +
                "   }\n" +
                "}\n";
        Search sea = new Search.Builder(json).addType("emps").addIndex("test").build();
        //这个execute返回的结果就是json结果中第一个hits下面的内容
        SearchResult execute = jestClient.execute(sea);
        //获取数据
        List<SearchResult.Hit<Employee, Void>> hits = execute.getHits(Employee.class);
        //遍历
        for (SearchResult.Hit<Employee, Void> employee : hits) {
            System.out.println(employee.index);
            System.out.println(employee.type);
            System.out.println(employee.score);
            System.out.println(employee.source.getEmpid()
                    +","+employee.source.getAge()
                    +","+employee.source.getBalance()
                    +","+employee.source.getName()
                    +","+employee.source.getGender()
                    +","+employee.source.getHobby());
        }
        //获取聚合的字段
        MetricAggregation aggregations = execute.getAggregations();
        //获取count
        TermsAggregation emp_count = aggregations.getTermsAggregation("emps_count");
        for (TermsAggregation.Entry bucket : emp_count.getBuckets()) {
            System.out.println(bucket.getKey()+","+bucket.getCount());
            //获取子聚合
            AvgAggregation gender_avg_age = bucket.getAvgAggregation("gender_avg_age");
            System.out.println(gender_avg_age.getAvg());
        }

//        System.out.println(execute.getResponseCode());
    }

    /**
     * 使用search查询封装之后对象
     * 使用对象封装参数
     * 案例：读取聚合查询之后的操作
     *
     */
    @Test
    public void read2() throws IOException {
    //封装quert{matchxxx}部分
        MatchQueryBuilder match = new MatchQueryBuilder("hobby", "购物");
     //封装agg部分 使用AggregationBuilders构造
        TermsAggregationBuilder agg = AggregationBuilders.terms("emps_count")
                .field("gender.keyword").size(10)
                .subAggregation(AggregationBuilders.avg("gender_avg_age").field("age"));
      //将query和agg整合 :SearchSourceBuilder是个结构体
        SearchSourceBuilder struct = new SearchSourceBuilder().query(match).aggregation(agg);
        //查询数据
        Search sea = new Search.Builder(struct.toString()).addType("emps").addIndex("test").build();
        SearchResult execute = jestClient.execute(sea);

        //获取聚合的字段
        MetricAggregation aggregations = execute.getAggregations();
        //获取count
        TermsAggregation emp_count = aggregations.getTermsAggregation("emps_count");
        for (TermsAggregation.Entry bucket : emp_count.getBuckets()) {
            System.out.println(bucket.getKey() + "," + bucket.getCount());
            //获取子聚合
            AvgAggregation gender_avg_age = bucket.getAvgAggregation("gender_avg_age");
            System.out.println(gender_avg_age.getAvg());
        }
    }


    @After
    public void close() throws IOException {
        //关闭连接
        jestClient.close();
    }





    }

