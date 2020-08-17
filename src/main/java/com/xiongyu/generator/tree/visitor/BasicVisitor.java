package com.xiongyu.generator.tree.visitor;


import com.xiongyu.entity.Table;
import com.xiongyu.generator.config.TablesConfig;
import com.xiongyu.generator.tree.Element;

public class BasicVisitor extends VisitorSupport {

  public BasicVisitor(Table table, TablesConfig tablesConfig) {
    super(table, tablesConfig);
  }

  @Override
  public void visit(com.xiongyu.generator.tree.Class aClass) {
    super.visit(aClass);
  }

  @Override
  public void visit(com.xiongyu.generator.tree.Field field) {
    super.visit(field);
    if (!field.getFieldName().equals(Element.SERIAL_VERSION_UID)) {
      field.setHasDefaultValue(false);
    }
  }
}
