package com.github.chaosfirebolt.converter.api.initialization.source;

import java.io.InputStream;

/**
 * Generates an input stream from a resource on the classpath.
 *
 * @param <StreamResult> type of the data read from the stream
 */
public abstract class ClassPathInputStreamSource<StreamResult> extends InputStreamSource<StreamResult> {

  private final ClassLoader classLoader;

  /**
   * Constructs a new instance from provided path to resource and classloader.
   *
   * @param path        path to the resource
   * @param classLoader class loader to create an input stream for the resource
   */
  public ClassPathInputStreamSource(String path, ClassLoader classLoader) {
    super(path);
    this.classLoader = classLoader;
  }

  /**
   * Utility constructor using the system classloader.
   *
   * @param path path to the resource
   */
  public ClassPathInputStreamSource(String path) {
    this(path, ClassLoader.getSystemClassLoader());
  }

  @Override
  protected InputStream createInputStream(String path) {
    return this.classLoader.getResourceAsStream(path);
  }
}
