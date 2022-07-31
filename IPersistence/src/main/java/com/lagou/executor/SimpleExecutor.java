package com.lagou.executor;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.statementHandler.SimpleStatementHandler;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {

        // 1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //2. 转换sql语句：insert into user(username) values(#{username})，转换过程中，还需要对#{}里面的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        //3. 创建SimpleStatementHandler
        SimpleStatementHandler simpleStatementHandler = new SimpleStatementHandler();

        //4. 执行查询
        if(params == null){
            return simpleStatementHandler.executeQuery(connection, mappedStatement, boundSql, null);
        }
        else{
            return simpleStatementHandler.executeQuery(connection, mappedStatement, boundSql, params[0]);
        }
    }



    @Override
    public int doInsert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        if(params == null){
            throw new RuntimeException("Insert function must has parameter.");
        }
        return doExecute(configuration, mappedStatement, params);
    }

    @Override
    public int doUpdate(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        if(params == null){
            throw new RuntimeException("Update function must has parameter.");
        }

        return doExecute(configuration, mappedStatement, params);
    }

    @Override
    public int doDelete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        if(params == null){
            throw new RuntimeException("Delete function must has parameter.");
        }
        return doExecute(configuration, mappedStatement, params);
    }


    public int doExecute(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception{

        // 1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        //2. 转换sql语句：insert into user(username) values(#{username})，转换过程中，还需要对#{}里面的值进行解析存储
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);

        //3. 创建SimpleStatementHandler
        SimpleStatementHandler simpleStatementHandler = new SimpleStatementHandler();

        return simpleStatementHandler.executeUpdate(connection, mappedStatement, boundSql, params[0]);

    }


    @Override
    public void close(Configuration configuration) throws Exception {
        Connection connection = configuration.getDataSource().getConnection();
        connection.close();
    }


    /**
     * 完成对#{}的解析工作：1.将#{}使用？进行代替；2.解析出#{}里面的值进行存储
     * 需要借助myBatis的工具类
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类：配置标记解析器（GenericTokenParser）来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{}里面解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql, parameterMappings);

        return boundSql;
    }






}
