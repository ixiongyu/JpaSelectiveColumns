package com.xiongyu.action;

import com.xiongyu.Holder;
import com.xiongyu.database.DatabaseDrivers;
import com.xiongyu.entity.ColumnSchema;
import com.xiongyu.entity.DatabasePluginTableSchema;
import com.xiongyu.entity.TableSchema;
import com.xiongyu.gui.AutoGeneratorSettingsDialog;
import com.xiongyu.i18n.LocaleContextHolder;
import com.xiongyu.util.DatabasePluginUtil;
import com.intellij.database.model.DasColumn;
import com.intellij.database.psi.DbDataSource;
import com.intellij.database.psi.DbTable;
import com.intellij.database.util.DasUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications.Bus;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.ui.Messages;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Jpa Support with database plugin
 */
public class JpaSupportWithDatabasePlugin extends AbstractPluginSupport {

  @Override
  public void actionPerformed(AnActionEvent e) {
    super.actionPerformed(e);

    List<DbTable> tables = DatabasePluginUtil.resolveDbTables(e.getData(LangDataKeys.PSI_ELEMENT_ARRAY));
    if (tables.isEmpty()) {
      Messages.showWarningDialog(LocaleContextHolder.format("not_select_valid_tables"), "JpaSupport");
      return;
    }
    resolveDatabaseVendor(tables.get(0).getDataSource());

    Holder.setSelectAllTables(true);

    List<TableSchema> tableSchemas = tables.stream().map(this::toTableSchema).collect(toList());
    AutoGeneratorSettingsDialog.show(tableSchemas, tableSchema -> {
      DbTable dbTable = ((DatabasePluginTableSchema) tableSchema).getDbTable();
      List<ColumnSchema> columnSchemas = new ArrayList<>();
      DasUtil.getColumns(dbTable).consumeEach(dasColumn -> {
        ColumnSchema columnSchema = new ColumnSchema();
        columnSchema.setColumnName(dasColumn.getName());
        columnSchema.setTableSchema(tableSchema.getTableSchema());
        columnSchema.setTableName(dasColumn.getTableName());
        columnSchema.setOrdinalPosition(dasColumn.getPosition());
        columnSchema.setDataType(dasColumn.getDataType().typeName);
        columnSchema.setColumnType(columnSchema.getDataType());
        boolean isAutoVal = dasColumn.getTable().getColumnAttrs(dasColumn).contains(DasColumn.Attribute.AUTO_GENERATED);
        columnSchema.setExtra(isAutoVal ? "auto_increment" : "");
        columnSchema.setColumnComment(StringUtils.trimToEmpty(dasColumn.getComment()));
        columnSchema.setIsNullable(dasColumn.isNotNull() ? "NO" : "YES");
        String defaultVal = dasColumn.getDefault();
        if (defaultVal != null && defaultVal.startsWith("'") && defaultVal.endsWith("'")) {
          defaultVal = defaultVal.substring(1, defaultVal.length() - 1);
        }
        columnSchema.setColumnDefault(defaultVal);
        columnSchema.setColumnKey(DasUtil.isPrimary(dasColumn) ? "PRI" : "");

        columnSchemas.add(columnSchema);
      });
      return columnSchemas;
    });
  }

  private TableSchema toTableSchema(DbTable dbTable) {
    TableSchema tableSchema = new DatabasePluginTableSchema(dbTable);
    tableSchema.setTableName(dbTable.getName());
    tableSchema.setTableComment(StringUtils.trimToEmpty(dbTable.getComment()));
    tableSchema.setTableSchema(DasUtil.getSchema(dbTable));
    return tableSchema;
  }

  private void resolveDatabaseVendor(DbDataSource dataSource) {
    String productName = "";
    try {
      // 确保方法存在
      dataSource.getClass().getMethod("getDatabaseProductName");
      productName = StringUtils.trimToEmpty(dataSource.getDelegate().getDatabaseProductName());
    } catch (Exception e) {
      // ignore exception
    }
    switch (productName) {
      case "PostgreSQL":
        Holder.registerDatabaseDrivers(DatabaseDrivers.POSTGRE_SQL);
        break;
      case "MySQL":
        Holder.registerDatabaseDrivers(DatabaseDrivers.MYSQL);
        break;
      default:
        Holder.registerDatabaseDrivers(DatabaseDrivers.MYSQL);
        Bus.notify(
            new Notification("JpaSupport", "Info", "Find unknown driver vendor " + productName + ", reset to MySQL",
                NotificationType.INFORMATION));
        break;
    }
  }
}
