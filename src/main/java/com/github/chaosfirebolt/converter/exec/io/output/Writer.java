package com.github.chaosfirebolt.converter.exec.io.output;

import java.io.IOException;

/**
 * Represents a writer of values.
 */
//TODO seal
//TODO make autocloseable
public interface Writer {

  /**
   * Writes a string.
   *
   * @param value string to write
   * @throws IOException in case of any IO failure
   */
  void write(String value) throws IOException;
}
