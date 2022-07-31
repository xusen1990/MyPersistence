package com.lagou.sqlSession;

import java.util.List;

public interface SqlSession {

    //查询所有
    public <E> List<E> selectList(String statementId, Object... params) throws Exception;

    //根据条件查询单个
    public <T> T selectOne(String statementId, Object... params) throws Exception;

    //插入
    public int insert(String statementId, Object... params) throws Exception;

    //更新
    public int update(String statementId, Object... params) throws Exception;

    //删除
    public int delete(String statementId, Object... params) throws Exception;

    //关闭连接
    public void close() throws Exception;

    //为Dao接口实现代理实现类
    public <T> T getMapper(Class<?> mapperClass);
}
