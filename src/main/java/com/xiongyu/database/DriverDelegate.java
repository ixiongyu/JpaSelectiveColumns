package com.xiongyu.database;

import lombok.Getter;
import lombok.experimental.Delegate;

import java.sql.Driver;

public class DriverDelegate implements Driver {

  @Delegate
  @Getter
  private final Driver driver;
  @Getter
  private final DatabaseDrivers databaseDrivers;

  public DriverDelegate(Driver driver, DatabaseDrivers databaseDrivers) {
    this.driver = driver;
    this.databaseDrivers = databaseDrivers;
  }
}
