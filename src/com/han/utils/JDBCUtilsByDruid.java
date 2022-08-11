package com.han.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @PACKAGE_NAME: com.han.utils
 * @Author XSH
 * @Date 2022-08-06 12:08
 * @Version 1.0
 * 描述：  使用druid连接池获取数据库连接。和使用dbUtils工具包封装返回数据。
 **/
public class JDBCUtilsByDruid {

    static DataSource ds;

    static {
        try {

            Properties properties = new Properties();
            properties.load(new FileInputStream("src//druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }




    public static void close(ResultSet set, Statement statement, Connection connection) {

        try{
            if (set != null)
                set.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void close(Connection connection) {

        try{
            if (connection != null)
                connection.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
