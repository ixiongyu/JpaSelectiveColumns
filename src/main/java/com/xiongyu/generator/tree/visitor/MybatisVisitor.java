package com.xiongyu.generator.tree.visitor;


import com.xiongyu.entity.Table;
import com.xiongyu.generator.config.TablesConfig;
import com.xiongyu.generator.tree.Element;
import com.xiongyu.generator.tree.Field;

public class MybatisVisitor extends VisitorSupport {

  public MybatisVisitor(Table table, TablesConfig tablesConfig) {
    super(table, tablesConfig);
  }

  @Override
  public void visit(Field field) {
    super.visit(field);
    if (!field.getFieldName().equals(Element.SERIAL_VERSION_UID)) {
      field.setHasDefaultValue(false);
    }
  }
}
