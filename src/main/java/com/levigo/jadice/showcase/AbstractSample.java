package com.levigo.jadice.showcase;

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
