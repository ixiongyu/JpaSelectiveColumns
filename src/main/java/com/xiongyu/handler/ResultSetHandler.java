package com.xiongyu.handler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author xiongyu
 */
public interface ResultSetHandler<T> {

  List<T> handle(ResultSet rs) throws SQLException;
}
