package com.github.chaosfirebolt.converter.exec;

import com.github.chaosfirebolt.converter.exec.io.Delimiter;
import com.github.chaosfirebolt.converter.exec.io.input.DefaultReader;
import com.github.chaosfirebolt.converter.exec.io.output.DefaultWriter;

/**
 * Entry point.
 */
public class Main {

  /**
   * Main method.
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    Delimiter delimiter = new Delimiter(";");
    ConsoleRunner runner = new ConsoleRunner(() -> DefaultReader.builder().setDelimiter(delimiter).setInputStream(System.in).build(), () -> DefaultWriter.builder().setDelimiter(delimiter).setOutputStream(System.out).build());
    runner.run();
  }
}
