package com.lagou.parameterHandler;

import com.lagou.executor.BoundSql;

import java.sql.PreparedStatement;

public interface ParameterHandler {

    void setParameters(PreparedStatement ps, BoundSql boundSql, String parameterType, Object parameter) throws Exception;
}
