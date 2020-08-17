package com.xiongyu.generator.source;

import com.xiongyu.util.VelocityUtil;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.function.Supplier;

public abstract class AbstractSourceParser implements SourceParser, VelocityEngineAware {

  protected VelocityEngine velocityEngine;
  protected String encoding;

  protected String evaluate(VelocityContext ctx, Supplier<String> templateProvider) {
    VelocityUtil.fillContext(ctx);
    try {
      StringWriter writer = new StringWriter();
      velocityEngine.evaluate(ctx, writer, getClass().getName(), templateProvider.get());
      return writer.toString();
    } catch (Exception e) {
      throw new EvaluateSourceCodeException(e);
    }
  }

  @Override
  public void setVelocityEngine(VelocityEngine ve, String encoding) {
    this.velocityEngine = ve;
    this.encoding = encoding;
  }
}
