package com.lagou.executor;

import com.lagou.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

//存放转换后的sql语句，以及#{}解析的字段名称
public class BoundSql {

    private String sqlText; //解析过后的SQL

    private List<ParameterMapping> parameterMappingList = new ArrayList<>(); //#{}解析的字段名称集合

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
