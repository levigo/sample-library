package com.levigo.jadice.showcase;


public abstract class AbstractSample implements Sample {

  private final Class<?>[] classes;
  private final String name;

  public AbstractSample(String name, Class<?>... classes) {
    super();
    this.name = name;
    this.classes = classes;
  }

  @Override
  public final Class<?>[] getClasses() {
    return classes;
  }

  @Override
  public final String name() {
    return name;
  }
}
