package com.github.chaosfirebolt.converter.exec.io.output;

import java.io.Closeable;
import java.io.IOException;

/**
 * Represents a writer of values.
 */
public sealed interface Writer extends Closeable permits DefaultWriter {

  /**
   * Writes a string.
   *
   * @param value string to write
   * @throws IOException in case of any IO failure
   */
  void write(String value) throws IOException;
}
