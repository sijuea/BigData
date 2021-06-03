package $03hive.kylin;

import java.sql.*;
import java.util.Properties;

/**
 * update by sijue on 2021/5/24
 */
public class KylinJDBCTest {

    public static void main(String[] args) throws Exception {
        Driver driver = (Driver) Class.forName("org.apache.kylin.jdbc.Driver").newInstance();
        //这个sql必须是cube构建时候执行过的sql，随便执行的sql是没有结果的
        String sql="SELECT\n" +
                "DIM_BASE_PROVINCE.PROVINCE_NAME as DIM_BASE_PROVINCE_PROVINCE_NAME\n" +
                ",DIM_BASE_PROVINCE.REGION_NAME as DIM_BASE_PROVINCE_REGION_NAME\n" +
                ",DIM_USER_INFO_VIEW.USER_LEVEL as DIM_USER_INFO_VIEW_USER_LEVEL\n" +
                ",DIM_USER_INFO_VIEW.GENDER as DIM_USER_INFO_VIEW_GENDER\n" +
                ",DWD_ORDER_INFO.ORIGINAL_AMOUNT as DWD_ORDER_INFO_ORIGINAL_AMOUNT\n" +
                ",DWD_ORDER_INFO.FINAL_AMOUNT as DWD_ORDER_INFO_FINAL_AMOUNT\n" +
                " FROM GMALL.DWD_ORDER_INFO as DWD_ORDER_INFO\n" +
                "LEFT JOIN GMALL.DIM_BASE_PROVINCE as DIM_BASE_PROVINCE\n" +
                "ON DWD_ORDER_INFO.PROVINCE_ID = DIM_BASE_PROVINCE.ID\n" +
                "LEFT JOIN GMALL.DIM_USER_INFO_VIEW as DIM_USER_INFO_VIEW\n" +
                "ON DWD_ORDER_INFO.USER_ID = DIM_USER_INFO_VIEW.ID\n" +
                "WHERE 1=1";
        Properties properties=new Properties();
        properties.put("user", "ADMIN");
        properties.put("password", "KYLIN");
        Connection connection =driver.connect("jdbc:kylin://hadoop102:7070/gmall", properties);

        PreparedStatement ps = connection.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        while(resultSet.next()){

//            System.out.println(resultSet.getString("PROVINCE_NAME") + "," + resultSet.getString("GENDER") + "," +
//                    resultSet.getBigDecimal("sum_original_amount") + "," +
//                    resultSet.getBigDecimal("sum_final_amount"));

            System.out.println(resultSet.getString("DIM_USER_INFO_VIEW_GENDER"));

        }

        resultSet.close();
        ps.close();
        connection.close();

    }
}
