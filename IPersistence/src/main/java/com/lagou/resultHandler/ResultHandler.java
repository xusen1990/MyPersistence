package com.lagou.resultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultHandler<E> {
    List<E> handleResult(String resultType, ResultSet resultSet) throws Exception;
}
