package com.github.chaosfirebolt.converter.exec.io.output;

/**
 * Factory for {@link Writer}s.
 */
public interface WriterFactory {

  /**
   * Creates a writer.
   *
   * @return an instance of {@link Writer}
   */
  Writer createWriter();
}
