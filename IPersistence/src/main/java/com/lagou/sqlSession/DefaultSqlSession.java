package com.lagou.sqlSession;

import com.lagou.executor.SimpleExecutor;
import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.pojo.SqlCommandType;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {


    private Configuration configuration;

    private SimpleExecutor simpleExecutor = new SimpleExecutor();

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = getMappedStatementWithVerification(configuration, statementId, SqlCommandType.SELECT);
        //将要去完成对SimpleExecutor里的query方法的调用
        List<E> list = simpleExecutor.query(configuration, mappedStatement, params);
        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<T> objects = selectList(statementId, params);
        if(objects.size() == 1){
            return objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }
    }

    @Override
    public int insert(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = getMappedStatementWithVerification(configuration, statementId, SqlCommandType.INSERT);
        //将要去完成对SimpleExecutor里的doInsert方法的调用
        int res = simpleExecutor.doInsert(configuration, mappedStatement, params);
        return res;
    }

    @Override
    public int update(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = getMappedStatementWithVerification(configuration, statementId, SqlCommandType.UPDATE);
        //将要去完成对SimpleExecutor里的doUpdate方法的调用
        int res = simpleExecutor.doUpdate(configuration, mappedStatement, params);
        return res;
    }

    @Override
    public int delete(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = getMappedStatementWithVerification(configuration, statementId, SqlCommandType.DELETE);
        //将要去完成对SimpleExecutor里的doDelete方法的调用
        int res = simpleExecutor.doDelete(configuration, mappedStatement, params);
        return res;
    }



    @Override
    public void close() throws Exception{
        simpleExecutor.close(configuration);
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 使用JDK动态代理来为Dao接口生成代理对象，并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 底层都还是去执行JDBC代码 // 根据不同情况去调用selectList或者selectOne
                // 准备参数 1：statementId: sql语句的唯一标识：namespace.id = 接口全限定名.方法名
                // 方法名：findAll
                String name = method.getName();
                String className = method.getDeclaringClass().getName();

                String statementId = className + "." + name;

                // 准备参数2：params：args


                MappedStatement mappedStatement = configuration.getMapStatementMap().get(statementId);
                SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

                switch (sqlCommandType) {
                    case SELECT:
                        //判断当前方法返回的类型，如果是集合就调用selectList,如果是对象就调用selectOne
                        //获取被调用方法的返回值类型
                        Type genericReturnType = method.getGenericReturnType();
                        // 判断了是否进行了泛型类型参数化
                        if (genericReturnType instanceof ParameterizedType) {
                            List<Object> objects = selectList(statementId, args);
                            return objects;
                        }

                        return selectOne(statementId, args);

                    case INSERT:
                        return insert(statementId, args);

                    case UPDATE:
                        return update(statementId, args);

                    case DELETE:
                        return delete(statementId, args);
                    default:
                        return null;
                }
            }
        });

        return (T) proxyInstance;
    }


    /**
     * get mappedStatement and check if label is consistent with statementId
     * @param configuration
     * @param statementId
     * @param sqlCommandType
     * @return
     */
    private MappedStatement getMappedStatementWithVerification(Configuration configuration, String statementId, SqlCommandType sqlCommandType){
        MappedStatement mappedStatement = configuration.getMapStatementMap().get(statementId);
        //判断标签(所调用的CRUD方法)是不是与statementId一致
        if(mappedStatement.getSqlCommandType() != sqlCommandType){
            throw new RuntimeException(statementId + " is not a " + sqlCommandType.name() + " function.");
        }
        return mappedStatement;
    }

}
