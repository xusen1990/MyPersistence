package com.lagou.parameterHandler;

import com.lagou.executor.BoundSql;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ToolUtils;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.List;

public class SimpleParameterHandler implements ParameterHandler {


    @Override
    public void setParameters(PreparedStatement ps, BoundSql boundSql, String parameterType, Object parameter) throws Exception {

        Class<?> parameterTypeClass = ToolUtils.getClassType(parameterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            // content对应#{}里面的值
            String content = parameterMapping.getContent();

            //反射
            Field declaredField = parameterTypeClass.getDeclaredField(content);  //获取属性对象
            // 防止是私有的，设置暴力访问
            declaredField.setAccessible(true);
            // 获取查询参数对象的属性content对应的值
            Object o = declaredField.get(parameter);//params[0]就是查询条件user对象

            // 处理对象PreparedStatement是从1开始设置，设置参数
            ps.setObject(i+1, o);
        }

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
