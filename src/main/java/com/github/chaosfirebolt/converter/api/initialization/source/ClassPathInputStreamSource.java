package com.github.chaosfirebolt.converter.api.initialization.source;

import java.io.InputStream;

/**
 * Generates an input stream from a resource on the classpath.
 *
 * @param <StreamResult> type of the data read from the stream
 */
public abstract class ClassPathInputStreamSource<StreamResult> extends InputStreamSource<StreamResult> {

  private final ResourceFactory resourceFactory;

  /**
   * Constructs a new instance from provided path to resource and classloader.
   *
   * @param path        path to the resource
   * @param classLoader class loader to create an input stream for the resource
   * @deprecated since 3.4.2, prefer {@link #ClassPathInputStreamSource(String, Class)}
   */
  @Deprecated(forRemoval = true, since = "3.4.2")
  public ClassPathInputStreamSource(String path, ClassLoader classLoader) {
    super(path);
    this.resourceFactory = new ClassLoaderResourceFactory(classLoader);
  }

  /**
   * Utility constructor using the system classloader.
   *
   * @param path path to the resource
   * @deprecated since 3.4.2, prefer {@link #ClassPathInputStreamSource(String, Class)}
   */
  @Deprecated(forRemoval = true, since = "3.4.2")
  public ClassPathInputStreamSource(String path) {
    this(path, ClassLoader.getSystemClassLoader());
  }

  public ClassPathInputStreamSource(String path, Class<?> classLoader) {
    super(path);
    this.resourceFactory = new ClassResourceFactory(classLoader);
  }

  @Override
  protected InputStream createInputStream(String path) {
    return resourceFactory.create(path);
  }
}
