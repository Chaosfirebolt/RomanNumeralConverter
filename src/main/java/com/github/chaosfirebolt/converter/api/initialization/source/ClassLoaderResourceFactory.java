package com.github.chaosfirebolt.converter.api.initialization.source;

import java.io.InputStream;

/**
 * Internal!
 * For backwards compatibility.
 */
class ClassLoaderResourceFactory implements ResourceFactory {

  private final ClassLoader classLoader;

  ClassLoaderResourceFactory(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public InputStream create(String path) {
    return classLoader.getResourceAsStream(path);
  }
}
