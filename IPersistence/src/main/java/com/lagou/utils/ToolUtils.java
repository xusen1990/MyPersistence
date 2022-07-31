package com.lagou.utils;

public class ToolUtils {

    // 通过类的全路径，获取该类
    public static Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if(parameterType!= null && !"".equals(parameterType)){
            Class<?> aClass = Class.forName(parameterType);
            return aClass;
        }
        return null;
    }
}
