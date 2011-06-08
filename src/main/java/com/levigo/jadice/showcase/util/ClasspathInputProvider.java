package com.levigo.jadice.showcase.util;

import java.io.IOException;
import java.io.InputStream;

import com.levigo.util.base.Provider;

public final class ClasspathInputProvider implements Provider<InputStream, IOException> {

  private static final String BASE = "/com/levigo/jadice/showcase/read/";
  private final String resourceName;


  public ClasspathInputProvider(String name) {
    super();
    this.resourceName = BASE + name;
  }


  @Override
  public InputStream get() throws IOException {
    return getClass().getResourceAsStream(resourceName);
  }

}