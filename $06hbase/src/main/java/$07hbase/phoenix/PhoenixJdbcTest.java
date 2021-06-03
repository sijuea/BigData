package $07hbase.phoenix;


import org.apache.phoenix.queryserver.client.ThinClientUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PhoenixJdbcTest {

    private Connection connection;
    private PreparedStatement statement;
    @Before
    public void init() throws Exception{
        //1、加载驱动
        Class.forName("org.apache.phoenix.queryserver.client.Driver");
        //2、获取connection
        String url = ThinClientUtil.getConnectionUrl("hadoop102", 8765);
        System.out.println(url);
        connection  = DriverManager.getConnection(url);
        //设置自动提交
        connection.setAutoCommit(true);
    }
    @Test
    public void createTable() throws Exception{


        //3、获取statement
        String sql = "create table student(" +
                "id varchar primary key," +
                "name varchar," +
                "age varchar)column_encoded_bytes=0";
        statement = connection.prepareStatement(sql);
        //4、执行sql
        statement.execute();

    }

    /**
     * 插入数据
     * @throws Exception
     */
    @Test
    public void insert() throws Exception{
        //获取statement
        String sql = "upsert into student values(?,?,?)";
        statement = connection.prepareStatement(sql);

        //赋值
        statement.setString(1,"1001");
        statement.setString(2,"lisi");
        statement.setString(3,"33");

        //执行sql
        statement.executeUpdate();

        //connection.commit();

    }

    /**
     * 批量插入
     * @throws Exception
     */
    @Test
    public void insertBatch() throws Exception{
        //获取statement
        String sql = "upsert into student values(?,?,?)";
        statement = connection.prepareStatement(sql);

        //封装数据
        for(int i=0;i<=1200;i++){

            statement.setString(1,"100"+i);
            statement.setString(2,"zhangsan-"+i);
            statement.setString(3,(20+i)+"");
            //将当前数据插入添加到一个批次中
            statement.addBatch();

            if(i%500==0){
                //执行一个批次
                statement.executeBatch();
                //清空批次
                statement.clearBatch();
            }
        }

        //执行最后一个不满500的批次数据
        statement.executeBatch();


    }

    /**
     * 查询数据
     * @throws Exception
     */
    @Test
    public void query() throws Exception{

        //获取statement
        String sql = "select * from student where id>?";
        statement = connection.prepareStatement(sql);
        //赋值
        statement.setString(1,"10011");
        //执行
        ResultSet resultSet = statement.executeQuery();
        //结果展示
        while (resultSet.next()){
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            String age = resultSet.getString("age");
            System.out.println("id="+id+",name="+name+",age="+age);
        }
    }

    /**
     * 删除数据
     * @throws Exception
     */
    @Test
    public void delete() throws Exception{
        //获取statement
        String sql = "delete from student where id>?";
        statement = connection.prepareStatement(sql);
        //参数赋值
        statement.setString(1,"1001");
        //执行
        statement.executeUpdate();
    }

    /**
     * 删除表
     * @throws Exception
     */
    @Test
    public void dropTable() throws Exception{

        String sql = "drop table student";
        statement = connection.prepareStatement(sql);

        statement.execute();
    }

    @After
    public void close() throws Exception{
        //5、关闭
        if(statement!=null)
            statement.close();
        if(connection!=null)
            connection.close();
    }
}
