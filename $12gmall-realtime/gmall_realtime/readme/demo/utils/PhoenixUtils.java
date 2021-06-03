package com.demo.utils;


import com.demo.entity.StartUpLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.phoenix.jdbc.PhoenixDriver;
//import org.apache.phoenix.queryserver.client.ThinClientUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

@Slf4j
public class PhoenixUtils  implements Serializable {



//    public PhoenixUtils() {
//        init();
//    }

    public  static Connection init() {
         Connection connection = null;
        try {
            //1、加载驱动
            Class.forName("org.apache.phoenix.queryserver.client.Driver");
            //2、获取connection
//            String url = ThinClientUtil.getConnectionUrl("hadoop102", 8765);
//            System.out.println(url);
            connection  = DriverManager.getConnection("jdbc:phoenix:hadoop102,hadoop103,hadoop104:2181");

            //设置自动提交
            connection.setAutoCommit(false);
        }catch (Exception e){
            log.info("connect phoenix error");
        }
        return connection;
    }


    /**
     * 插入数据
     * @throws Exception
     */
    public static void insert(Connection connection,
                              StartUpLog log) throws Exception{
        //获取statement
        String sql = "upsert into GMALL2021_DAU  values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        //赋值
        statement.setString(1,log.getMid());
        statement.setString(2,log.getUid());
        statement.setString(3,log.getAppid());
        statement.setString(4,log.getArea());
        statement.setString(5,log.getOs());
        statement.setString(6,log.getCh());
        statement.setString(7,log.getType());
        statement.setString(8,log.getVs());
        statement.setString(9,log.getLogDate());
        statement.setString(10,log.getLogHour());
        statement.setLong(11,log.getTs());

        //执行sql
        statement.executeUpdate();

        connection.commit();

    }

    /**
     * 批量插入
     * @throws Exception
     */

    public void insertBatch(Connection connection,
                            PreparedStatement statement,List<StartUpLog> logs) throws Exception{
        //获取statement
        String sql = "upsert into GMALL2021_DAU  values(?,?,?,?,?,?,?,?,?,?,?)";
        statement = connection.prepareStatement(sql);

        //封装数据
        for(int i=0;i<logs.size();i++){
            StartUpLog log=logs.get(i);
            statement.setString(1,log.getMid());
            statement.setString(2,log.getUid());
            statement.setString(3,log.getAppid());
            statement.setString(4,log.getArea());
            statement.setString(5,log.getOs());
            statement.setString(6,log.getCh());
            statement.setString(7,log.getType());
            statement.setString(8,log.getVs());
            statement.setString(9,log.getLogDate());
            statement.setString(10,log.getLogHour());
            statement.setLong(11,log.getTs());
            //将当前数据插入添加到一个批次中
            statement.addBatch();
            //执行一个批次
            statement.executeBatch();

//            if(i%500==0){
//                //执行一个批次
//                statement.executeBatch();
//                //清空批次
//                statement.clearBatch();
//            }
        }

        //执行最后一个不满500的批次数据
//        statement.executeBatch();


    }

    /**
     * 查询数据
     * @throws Exception
     */
    @Test
    public void query(Connection connection,
                      PreparedStatement statement) throws Exception{

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
    public void delete(Connection connection,
                       PreparedStatement statement) throws Exception{
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
    public void dropTable(Connection connection,
                          PreparedStatement statement) throws Exception{

        String sql = "drop table student";
        statement = connection.prepareStatement(sql);

        statement.execute();
    }


    public void close(Connection connection,
                      PreparedStatement statement) throws Exception{
        //5、关闭
        if(statement!=null) {
            statement.close();
        }
        if(connection!=null) {
            connection.close();
        }
    }
}
