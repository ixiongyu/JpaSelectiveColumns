package com.xiongyu.component;

import com.xiongyu.Holder;
import com.xiongyu.database.DatabaseDrivers;
import com.xiongyu.i18n.LocaleContextHolder;
import com.xiongyu.i18n.LocaleItem;
import com.xiongyu.state.DatabaseSettingsState;
import lombok.Data;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.util.Locale;
import java.util.Objects;

@Data
public class DatabaseSettings {

  private JPanel rootComponent;
  private JTextField textHost;
  private JTextField textUsername;
  private JTextField textDatabase;
  private JPasswordField textPassword;
  private JTextField textPort;
  private JLabel lblSelectLanguage;
  private JTextField textConnectionUrl;
  private JComboBox<DatabaseDrivers> cbxSelectDatabase;
  private JComboBox<LocaleItem> cbxSelectLanguage;
  private JCheckBox chkBoxRequireSavePassword;

  public void setData(DatabaseSettingsState data) {
    textHost.setText(data.getHost());
    textPort.setText(data.getPort() + "");
    textUsername.setText(data.getUsername());
    textDatabase.setText(data.getDatabase());
    textConnectionUrl.setText(data.getUrl());
    chkBoxRequireSavePassword.setSelected(data.isRequireSavePassword());

    // 选择语言
    Locale locale = LocaleContextHolder.getCurrentLocale();
    cbxSelectLanguage.removeAllItems();
    for (LocaleItem localeItem : LocaleContextHolder.LOCALE_ITEMS) {
      cbxSelectLanguage.addItem(localeItem);
      if (locale.equals(localeItem.getLocale())) {
        cbxSelectLanguage.setSelectedItem(localeItem);
      }
    }

    // 选择数据库
    cbxSelectDatabase.removeAllItems();
    String databaseVendor = data.getDatabaseDriver();
    for (DatabaseDrivers databaseDrivers : DatabaseDrivers.values()) {
      cbxSelectDatabase.addItem(databaseDrivers);
      if (databaseDrivers.toString().equalsIgnoreCase(databaseVendor)) {
        cbxSelectDatabase.setSelectedItem(databaseDrivers);
        Holder.registerDatabaseDrivers(databaseDrivers);
      }
    }
  }

  public void getData(DatabaseSettingsState data) {
    data.setHost(textHost.getText());
    if (NumberUtils.isDigits(textPort.getText())) {
      data.setPort(Integer.parseInt(textPort.getText()));
    }
    data.setUsername(textUsername.getText());
    data.setDatabase(textDatabase.getText());
    data.setUrl(textConnectionUrl.getText());
    data.setLanguage(((LocaleItem) Objects.requireNonNull(cbxSelectLanguage.getSelectedItem())).getLanguageTag());
    data.setDatabaseDriver(((DatabaseDrivers) Objects.requireNonNull(cbxSelectDatabase.getSelectedItem())).toString());
    data.setRequireSavePassword(chkBoxRequireSavePassword.isSelected());
  }
}
