package com.github.chaosfirebolt.converter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ArabicParserTests {

  private final ArabicParser arabicParser;

  public ArabicParserTests() {
    this.arabicParser = new ArabicParser();
  }

  @ParameterizedTest
  @MethodSource
  public void validInputShouldReturnCorrect(String input, String expected) {
    RomanInteger result = arabicParser.parse(input);
    assertEquals(expected, result.getRoman(), "Roman representation not as expected");
    assertEquals(Integer.parseInt(input.trim()), result.getArabic(), "Arabic representation not as expected");
  }

  private static Stream<Arguments> validInputShouldReturnCorrect() {
    return Stream.of(Arguments.of(" 1776", "MDCCLXXVI"), Arguments.of("1954 ", "MCMLIV"), Arguments.of("1990", "MCMXC"),
            Arguments.of("2014", "MMXIV"), Arguments.of("39", "XXXIX"), Arguments.of("246", "CCXLVI"),
            Arguments.of("207", "CCVII"), Arguments.of("1066", "MLXVI"), Arguments.of("3498", "MMMCDXCVIII"));
  }

  @ParameterizedTest
  @MethodSource
  public void invalidInputShouldThrowException(Class<? extends Exception> expectedException, String input) {
    Exception exc = assertThrows(expectedException, () -> arabicParser.parse(input), () -> expectedException.getSimpleName() + " should have been thrown for input - " + input);
    String message = exc.getMessage();
    assertTrue(message != null && !message.isEmpty(), "Error message expected, but not found");
  }

  private static Stream<Arguments> invalidInputShouldThrowException() {
    return Stream.of(Arguments.of(NumberFormatException.class, "-1"), Arguments.of(IllegalArgumentException.class, "4125"),
            Arguments.of(NumberFormatException.class, "MX"), Arguments.of(NumberFormatException.class, "f"),
            Arguments.of(NumberFormatException.class, ""));
  }
}
