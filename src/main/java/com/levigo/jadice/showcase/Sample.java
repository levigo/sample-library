package com.levigo.jadice.showcase;

public interface Sample {
  /**
   * @return all classes that make up the sample (for source code viewing)
   */
  Class<?>[] getClasses();

  String name();
}
