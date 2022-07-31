package com.lagou.executor;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    public int doInsert(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    public int doUpdate(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    public int doDelete(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;

    public void close(Configuration configuration) throws Exception;

}
