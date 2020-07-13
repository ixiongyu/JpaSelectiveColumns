package com.xiongyu.database;

/**
 * @author xiongyu
 * @version Create at ：2020/7/13 10:07 下午
 */

public enum Vendor {
    /**
     *
     */
    MYSQL("mysql"),
    ORACLE("oracle"),
    /**
     *
     */
    SQL_SERVER("sqlserver"),
    POSTGRE_SQL("postgre sql");
    private final String alias;

    Vendor(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
