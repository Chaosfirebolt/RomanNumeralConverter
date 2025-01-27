package com.github.chaosfirebolt.converter.exec.io.input;

import java.util.stream.Stream;

/**
 * Represents a reader of values to parse from a source.
 */
//TODO seal
public interface Reader {

  /**
   * Reads a source and returns a stream of elements to parse.
   * Each element in the stream is ready to be parsed.
   *
   * @return a stream of elements to parse
   */
  Stream<String> read();
}
