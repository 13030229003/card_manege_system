package com.han.dao;

import com.han.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @PACKAGE_NAME: com.han.dao
 * @Author XSH
 * @Date 2022-08-06 17:35
 * @Version 1.0
 * 描述：
 **/
public class BasicDAO<T> {

    private QueryRunner qr = new QueryRunner();


    public int update(String sql,Object... parameters) {

        Connection connection = null;

        try {
            connection = JDBCUtilsByDruid.getConnection();
            int update = qr.update(connection, sql, parameters);

            return update;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(connection);
        }


    }
    public List<T> queryMulti(String sql,Class<T> clazz,Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();

            List<T> query = qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);

            return query;


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(connection);
        }
    }

    public T querySingle(String sql,Class<T> clazz,Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            T query = qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);
            return query;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(connection);
        }
    }

    public Object queryScalar(String sql,Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConnection();
            Object query = qr.query(connection, sql, new ScalarHandler<>(), parameters);
            return query;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtilsByDruid.close(connection);
        }
    }

}
