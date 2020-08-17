package com.xiongyu.generator.source;

import com.xiongyu.entity.Table;
import com.xiongyu.generator.config.GeneratorConfig;

/**
 * 源码解析器
 */
public interface SourceParser {

  String parse(GeneratorConfig config, Table table);

  default String parse(GeneratorConfig config, Table table, String template) {
    throw new UnsupportedOperationException();
  }
}
