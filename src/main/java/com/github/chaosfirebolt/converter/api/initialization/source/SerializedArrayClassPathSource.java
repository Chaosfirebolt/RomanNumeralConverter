package com.github.chaosfirebolt.converter.api.initialization.source;

import com.github.chaosfirebolt.converter.RomanInteger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Reads serialized array from the class path.
 */
public class SerializedArrayClassPathSource extends ClassPathInputStreamSource<RomanInteger[]> {

  /**
   * @param path        path to the resource
   * @param classLoader classLoader, responsible for creating the input stream
   */
  public SerializedArrayClassPathSource(String path, ClassLoader classLoader) {
    super(path, classLoader);
  }

  /**
   * @param path path to the resource
   */
  public SerializedArrayClassPathSource(String path) {
    super(path);
  }

  @Override
  protected RomanInteger[] readInputStream(InputStream inputStream) throws IOException {
    ObjectInputStream stream = new ObjectInputStream(inputStream);
    try {
      return (RomanInteger[]) stream.readObject();
    } catch (ClassNotFoundException exc) {
      throw new IOException("Could read serialized roman integer array", exc);
    }
  }
}
