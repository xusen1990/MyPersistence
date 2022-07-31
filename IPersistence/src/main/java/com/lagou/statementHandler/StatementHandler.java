package com.lagou.statementHandler;

import com.lagou.executor.BoundSql;
import com.lagou.pojo.MappedStatement;
import com.lagou.resultHandler.ResultHandler;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler {

    int executeUpdate(Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Object parameter) throws Exception;

    <E> List<E> executeQuery(Connection connection, MappedStatement mappedStatement, BoundSql boundSql, Object parameter ) throws Exception;
}
