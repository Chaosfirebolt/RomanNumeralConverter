package com.github.chaosfirebolt.converter.exec.io;

/**
 * Represent possible channels for input/output.
 */
public enum IOType {

  /**
   * Channel for reading/writing on the console.
   */
  CONSOLE("c"),
  /**
   * Channel for reading/writing in files.
   */
  FILE("f");

  private final String shortName;

  IOType(String shortName) {
    this.shortName = shortName;
  }

  /**
   * Parses an {@link IOType} from provided option, ignoring the case.
   * Throws an {@link IllegalArgumentException}, in case of invalid option.
   *
   * @param option an option to parse
   * @return an instance of {@link IOType}
   * @throws IllegalArgumentException in case of invalid option
   */
  public static IOType fromOption(String option) {
    if (option == null) {
      return CONSOLE;
    }
    for (IOType type : values()) {
      if (option.equalsIgnoreCase(type.shortName)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid option");
  }
}
