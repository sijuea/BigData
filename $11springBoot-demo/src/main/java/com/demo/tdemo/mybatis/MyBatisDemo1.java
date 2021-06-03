package com.demo.tdemo.mybatis;

import com.demo.tdemo.pojo.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by VULCAN on 2021/5/25
 *
 *      SqlSessionFactory ------------> 创建 SqlSession
 *
 *      SqlSession：   类似JDBC中的Connection
 *
 *
 *      ①创建SqlSessionFactory
 *      ②使用SqlSessionFactory创建一个SqlSession
 *      ③SqlSession 将sql 发送给 mysql数据库执行
 */
public class MyBatisDemo1 {

    public static void main(String[] args) throws IOException {

        //基于一个xml配置文件创建SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //使用SqlSessionFactory创建一个SqlSession
        SqlSession session = sqlSessionFactory.openSession();
        try {

            /*
                    sqlSession 将sql 发送给 mysql数据库执行

                    selectOne(String statement, Object parameter);
                            String statement：  通过namespace.id找对应的sql语句
                            Object parameter： 传给语句的参数
             */
            Object o = session.selectOne("emps.sql1", 1);

            Employee employee = (Employee) o;

            System.out.println(employee);

        } finally {
            session.close();
        }

    }




}
