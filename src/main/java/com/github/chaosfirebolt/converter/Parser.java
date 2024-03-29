package com.github.chaosfirebolt.converter;

/**
 * Represents a parser.
 */
public sealed interface Parser permits BaseParser, DelegatingParser {

  /**
   * Parses provided string to a {@link RomanInteger}.
   *
   * @param input string for parsing.
   * @return object containing roman and arabic numeral.
   * @throws IllegalArgumentException if resulting, or provided arabic numeral, is not in the valid range for roman numerals.
   * @throws NumberFormatException    if provided string does not match format required by the implementing class
   */
  RomanInteger parse(String input);
}
