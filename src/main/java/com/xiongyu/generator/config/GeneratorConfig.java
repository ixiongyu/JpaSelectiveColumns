package com.xiongyu.generator.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GeneratorConfig {

  private DriverConfig driverConfig;
  private TablesConfig tablesConfig;
  private List<PluginConfig> pluginConfigs;
}
