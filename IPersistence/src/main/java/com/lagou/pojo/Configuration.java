package com.lagou.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private DataSource dataSource;

    // key: statementId
    private Map<String, MappedStatement> mapStatementMap = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMapStatementMap() {
        return mapStatementMap;
    }

    public void setMapStatementMap(Map<String, MappedStatement> mapStatementMap) {
        this.mapStatementMap = mapStatementMap;
    }
}
