package com.xiongyu.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xiongyu
 */
public interface RowHandler<T> {

  T handle(ResultSet row, int rowNum) throws SQLException;
}
