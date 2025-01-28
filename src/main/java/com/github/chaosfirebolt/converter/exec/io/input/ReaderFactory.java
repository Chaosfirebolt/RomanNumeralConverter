package com.github.chaosfirebolt.converter.exec.io.input;

/**
 * Factory for {@link Reader}s.
 */
public interface ReaderFactory {

  /**
   * Creates a reader.
   *
   * @return an instance of {@link Reader}
   */
  Reader createReader();
}
