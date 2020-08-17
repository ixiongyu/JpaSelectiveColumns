package com.xiongyu.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public enum Key {
  ;

  public static String createKey(String name) {
    return "com.ifengxue.com.xiongyu.jps-support." + name;
  }

  public static String createKey(String name, Object... args) {
    String prefix = createKey(name);
    String suffix = Arrays.stream(args)
        .map(Object::toString)
        .collect(joining("_"));
    if (StringUtils.isEmpty(suffix)) {
      return prefix;
    } else {
      return prefix + "_" + suffix;
    }
  }
}
