package com.lagou.statementHandler;

import com.lagou.executor.BoundSql;
import com.lagou.parameterHandler.SimpleParameterHandler;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.resultHandler.ResultHandler;
import com.lagou.resultHandler.SimpleResultHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleStatementHandler implements StatementHandler {


    @Override
    public int executeUpdate(Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Object parameter) throws Exception {

        // 1.获取预处理对象PreparedStatement
        PreparedStatement preparedStatement = prepareStatement(connection, mappedStatement, boundSql, parameter);
        // 2.执行sql
        int res = preparedStatement.executeUpdate();

        return res;
    }

    @Override
    public <E> List<E> executeQuery(Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Object parameter ) throws Exception {

        // 1.获取预处理对象PreparedStatement
        PreparedStatement preparedStatement = prepareStatement(connection, mappedStatement, boundSql, parameter);
        // 2.执行sql
        ResultSet resultSet = preparedStatement.executeQuery();

        // 3. 获取返回封装结果集类型
        String resultType = mappedStatement.getResultType();

        // 4.获取返回结果集
        SimpleResultHandler<E> resultHandler = new SimpleResultHandler<E>();

        List<E> objects = resultHandler.handleResult(resultType, resultSet);

        return objects;
    }


    private PreparedStatement prepareStatement(Connection connection, MappedStatement mappedStatement, BoundSql boundSql,  Object parameter) throws Exception {

        // 1. 获取预处理对象PreparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        if(parameter != null){
            // 2. 获取参数类型
            String parameterType = mappedStatement.getParameterType();

            // 3. 创建SimpleParameterHandler类
            SimpleParameterHandler simpleParameterHandler = new SimpleParameterHandler();

            // 4. 为预处理对象获取预处理对象PreparedStatement设置参数
            simpleParameterHandler.setParameters(preparedStatement, boundSql, parameterType, parameter);
        }

        return preparedStatement;
    }
}
