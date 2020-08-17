package com.xiongyu.jdbc.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowHandler<T> {

  T handle(ResultSet row, int rowNum) throws SQLException;
}
