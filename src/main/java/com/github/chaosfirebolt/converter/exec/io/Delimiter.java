package com.github.chaosfirebolt.converter.exec.io;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Represents a delimiter used to split a string.
 * An empty delimiter does not split the string.
 */
//TODO refactor into a record
public class Delimiter implements Function<String, String[]> {

  private final String value;

  private Delimiter(String value) {
    this.value = value;
  }

  /**
   * Creates a delimiter. Invoking this method with empty (null or empty string) value creates an empty delimiter.
   * See {@link #empty()}.
   *
   * @param value value of the delimiter
   * @return a delimiter instance
   */
  public static Delimiter of(String value) {
    if (isEmpty(value)) {
      return empty();
    }
    return new Delimiter(value);
  }

  private static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }

  /**
   * Creates an empty delimiter.
   *
   * @return an empty delimiter instance.
   */
  public static Delimiter empty() {
    return new Delimiter(null);
  }

  private Optional<String> regexValue() {
    if (isEmpty(value)) {
      return Optional.empty();
    }
    return Optional.of(Pattern.quote(value));
  }

  /**
   * Splits the line using this delimiter literal value.
   *
   * @param line line to split
   * @return an array of strings by splitting the input around matches of this delimiter
   */
  public String[] split(String line) {
    return regexValue()
            .map(line::split)
            .orElse(new String[]{line});
  }

  @Override
  public String[] apply(String line) {
    return split(line);
  }

  public String value() {
    return value;
  }
}
