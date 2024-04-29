package com.github.chaosfirebolt.converter.api.initialization.source;

import java.io.InputStream;

/**
 * Internal!
 */
class ClassResourceFactory implements ResourceFactory {

  private final Class<?> clazz;

  ClassResourceFactory(Class<?> clazz) {
    this.clazz = clazz;
  }

  @Override
  public InputStream create(String path) {
    return clazz.getResourceAsStream(path);
  }
}
