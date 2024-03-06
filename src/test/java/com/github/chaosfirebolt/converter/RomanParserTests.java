package com.github.chaosfirebolt.converter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RomanParserTests {

  private final RomanParser romanParser;

  public RomanParserTests() {
    this.romanParser = new RomanParser();
  }

  @ParameterizedTest
  @MethodSource
  public void validInputShouldReturnCorrectValue(String input, int expected) {
    RomanInteger result = this.romanParser.parse(input);
    assertEquals(expected, result.getArabic(), "Arabic representation not as expected");
    assertEquals(input.toUpperCase(Locale.ENGLISH).trim(), result.getRoman(), "Roman representation not as expected");
  }

  private static Stream<Arguments> validInputShouldReturnCorrectValue() {
    return Stream.of(Arguments.of("MDCCLXxVI", 1776), Arguments.of("McMLIV", 1954), Arguments.of("MCMXC", 1990),
            Arguments.of("MMXIV", 2014), Arguments.of("XXXIX", 39), Arguments.of("CCXLVI", 246),
            Arguments.of("CCvII", 207), Arguments.of("MLXVI", 1066), Arguments.of("IIII", 4),
            Arguments.of("VIIiI", 9), Arguments.of("XCIX", 99), Arguments.of("IC", 99),
            Arguments.of(" XIIX", 18), Arguments.of("IIXX ", 18), Arguments.of("IIIIII ", 6),
            Arguments.of("XXXXXX ", 60), Arguments.of("CM ", 900), Arguments.of("MDCCCCX ", 1910),
            Arguments.of("MCMX ", 1910), Arguments.of("MDCDIII", 1903), Arguments.of("MCMIII", 1903));
  }

  @ParameterizedTest
  @MethodSource
  public void invalidInputShouldThrowException(Class<? extends Exception> expectedException, String input) {
    Exception exc = assertThrows(expectedException, () -> this.romanParser.parse(input), () -> expectedException.getSimpleName() + " should have been thrown for input - " + input);
    String message = exc.getMessage();
    assertTrue(message != null && !message.isEmpty(), "Error message expected, but not found");
  }

  private static Stream<Arguments> invalidInputShouldThrowException() {
    return Stream.of(Arguments.of(NumberFormatException.class, "M M"), Arguments.of(IllegalArgumentException.class, "MmMM"),
            Arguments.of(IllegalArgumentException.class, "MmMDD"), Arguments.of(NumberFormatException.class, "D5"),
            Arguments.of(NumberFormatException.class, ""), Arguments.of(NumberFormatException.class, "W"),
            Arguments.of(NumberFormatException.class, "1"));
  }
}
