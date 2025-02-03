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
    JCommander jCommander = JCommander.newBuilder().addObject(configuration).programName("Roman Numeral Converter").build();
    jCommander.parse(args);
    if (configuration.isHelp()) {
      jCommander.usage();
      return;
    }
    ConsoleRunner runner = new ConsoleRunner(configuration, configuration);
    runner.run();
  }
}
