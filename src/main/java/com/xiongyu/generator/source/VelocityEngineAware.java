package com.xiongyu.generator.source;

import org.apache.velocity.app.VelocityEngine;

public interface VelocityEngineAware {

  void setVelocityEngine(VelocityEngine ve, String encoding);
}
