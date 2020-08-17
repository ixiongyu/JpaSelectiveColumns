package com.xiongyu.generator.tree.visitor;

import com.xiongyu.generator.tree.Class;
import com.xiongyu.generator.tree.Field;
import com.xiongyu.generator.tree.Import;
import com.xiongyu.generator.tree.Package;

public interface Visitor {

  void visit(Package  aPackage);

  void visit(Import anImport);

  void visit(Class aClass);

  void visit(Field field);
}
