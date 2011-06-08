package com.levigo.jadice.showcase;

import com.levigo.jadice.demo.BasicJadicePanel;
import com.levigo.jadice.document.read.fluent.Read;
import com.levigo.jadice.document.read.fluent.SyncReadConfigurer;
import com.levigo.jadice.swing.pageview.PageView;

public abstract class AbstractSample implements Sample {

  private final String name;
  private final Class<?>[] classes;

  public AbstractSample(String name, Class<?>... classes) {
    super();
    this.name = name;
    this.classes = classes;
  }

  @Override
  public final String name() {
    return name;
  }

  @Override
  public final Class<?>[] getClasses() {
    return classes;
  }
}
