package com.github.chaosfirebolt.converter.exec;

import com.beust.jcommander.JCommander;

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
    Configuration configuration = new Configuration();
    JCommander.newBuilder().addObject(configuration).build().parse(args);
    ConsoleRunner runner = new ConsoleRunner(configuration, configuration);
    runner.run();
  }
}
