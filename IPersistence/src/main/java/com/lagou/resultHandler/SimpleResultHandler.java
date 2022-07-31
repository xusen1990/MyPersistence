package com.lagou.resultHandler;


import com.lagou.utils.ToolUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class SimpleResultHandler<E> implements ResultHandler<E> {
    @Override
    public List<E> handleResult(String resultType, ResultSet resultSet) throws Exception {

        // 定义返回结果
        List<E> objects = new ArrayList<>();

        Class<?> resultTypeClass = ToolUtils.getClassType(resultType);
        while (resultSet.next()){
            //创建resultTypeClass这个类的一个对象
            E o = (E)resultTypeClass.newInstance();

            //元数据,借助元数据获得查询结果的字段名称，用该字段名称封装成结果集
            //要求数据库的字段名称和Java的POJO的属性名称完全相同
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 遍历columnName的下标要从1开始
            for (int i = 1; i <= metaData.getColumnCount() ; i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object value = resultSet.getObject(columnName);

                //使用内省，根据数据库表和实体的对应关系，完成封装
                // 对resultTypeClass类的columnName属性产生读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                //将具体的值封装到这个对象中
                writeMethod.invoke(o, value);
            }
            objects.add(o);

        }
        return objects;
    }

    // 通过类的全路径，获取该类
//    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
//        if(parameterType!= null && !"".equals(parameterType)){
//            Class<?> aClass = Class.forName(parameterType);
//            return aClass;
//        }
//        return null;
//    }
}
