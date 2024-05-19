package com.github.chaosfirebolt.converter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class RomanIntegerParseExtendedTests {

  private static final String ARABIC_ERROR_MESSAGE = "Arabic representation not as expected";
  private static final String ROMAN_ERROR_MESSAGE = "Roman representation not as expected";

  @BeforeAll
  public static void setUp() {
    RomanInteger.extend('k', 'n');
    RomanInteger.extend('R', 'T');
    RomanInteger.extend('Y', 'Z');
  }

  @AfterAll
  public static void cleanUp() {
    RomanInteger.clearExtensions();
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/extended-values.csv", numLinesToSkip = 1)
  public void parseFromArabic(int arabic, String roman) {
    RomanInteger result = RomanInteger.parse(Integer.toString(arabic));
    assertEquals(arabic, result.getArabic(), ARABIC_ERROR_MESSAGE);
    assertEquals(roman, result.getRoman(), ROMAN_ERROR_MESSAGE);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/extended-values.csv", numLinesToSkip = 1)
  public void parseFromRoman(int arabic, String roman) {
    RomanInteger result = RomanInteger.parse(roman);
    assertEquals(arabic, result.getArabic(), ARABIC_ERROR_MESSAGE);
    assertEquals(roman, result.getRoman(), ROMAN_ERROR_MESSAGE);
  }

  @ParameterizedTest
  @ValueSource(strings = {"ZZJ", "N8", "GH"})
  public void incorrectFormat_ShouldThrowNumberFormatException(String input) {
    assertExceptionWithMessage(input, NumberFormatException.class);
  }

  private static <E extends Exception> void assertExceptionWithMessage(String input, Class<E> expectedException) {
    E exc = assertThrows(expectedException, () -> RomanInteger.parse(input), () -> String.format("Expected '%s' for input '%s' was not thrown", expectedException.getSimpleName(), input));
    String message = exc.getMessage();
    assertTrue(message != null && !message.isBlank(), "Missing exception message");
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "4000000", "5000000", "ZZZZ", "YYYYYYYY", "ZZYYYY", "ZZZTZTT"})
  public void outsideOfRange_ShouldThrowIllegalArgumentException(String input) {
    assertExceptionWithMessage(input, IllegalArgumentException.class);
  }
}
