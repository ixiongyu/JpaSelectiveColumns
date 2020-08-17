package com.xiongyu.database;


import com.xiongyu.Holder;
import com.xiongyu.entity.Column;
import com.xiongyu.entity.ColumnSchema;
import com.xiongyu.entity.TableSchema;
import com.xiongyu.jdbc.Sql;
import com.xiongyu.jdbc.SqlBuilder;
import com.xiongyu.util.ColumnUtil;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.List;

public class MysqlDriverAdapter extends AbstractDriverAdapter {

  @Override
  protected String toConnectionUrl(String oldConnectionUrl, String host, String port, String username, String database,
      String params) {
    String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
    if (StringUtils.isNotBlank(params)) {
      connectionUrl += "?" + params;
    }
    return connectionUrl;
  }

  @Override
  public List<TableSchema> findDatabaseSchemas(String database) throws SQLException {
    Sql sql = SqlBuilder.newSelectBuilder(TableSchema.class)
        .select()
        .from()
        .where()
        .equal("tableSchema", database)
        .build();
    return Holder.getFastJdbc().find(sql.getSql(), TableSchema.class, sql.getArgs().toArray());
  }

  @Override
  public List<ColumnSchema> findTableSchemas(String database, String table) throws SQLException {
    Sql sql = SqlBuilder.newSelectBuilder(ColumnSchema.class)
        .select()
        .from()
        .where()
        .equal("tableSchema", database)
        .and().equal("tableName", table)
        .build();
    return Holder.getFastJdbc().find(sql.getSql(), ColumnSchema.class, sql.getArgs().toArray());
  }

  @Override
  public Column parseToColumn(ColumnSchema columnSchema, String removeFieldPrefix, boolean useWrapper,
                              boolean useJava8DataType) {
    Column column = new Column();
    column.setColumnName(columnSchema.getColumnName());
    column.setSort(columnSchema.getOrdinalPosition());
    column.setDbDataType(columnSchema.getDataType());
    column.setPrimary("PRI".equalsIgnoreCase(columnSchema.getColumnKey()));
    column.setNullable(!"NO".equalsIgnoreCase(columnSchema.getIsNullable()));
    column.setAutoIncrement(columnSchema.getExtra().contains("auto_increment"));
    column.setColumnComment(columnSchema.getColumnComment());
    column.setDefaultValue(columnSchema.getColumnDefault());
    ColumnUtil.parseColumn(this, column, removeFieldPrefix, useWrapper, useJava8DataType);
    return column;
  }
}
