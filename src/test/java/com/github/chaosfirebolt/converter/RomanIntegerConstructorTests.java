package com.github.chaosfirebolt.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RomanIntegerConstructorTests {

  @ParameterizedTest
  @CsvSource({"XL,40", "XXXX,40", "CD,400"})
  public void stringStringCtor_ValidInput_ShouldReturnCorrect(String roman, String arabic) {
    RomanInteger romanInteger = new RomanInteger(roman, arabic);
    assertEquals(roman, romanInteger.getRoman());
    assertEquals(arabic, Integer.toString(romanInteger.getArabic()));
  }

  @ParameterizedTest
  @CsvSource({"XX,40", "XL,30", "XV,11"})
  public void stringStringCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException(String roman, String arabic) {
    assertExceptionThrown(IllegalArgumentException.class, () -> new RomanInteger(roman, arabic));
  }

  private static void assertExceptionThrown(Class<? extends Exception> expectedException, Executable executable) {
    Exception exception = assertThrows(expectedException, executable, () -> String.format("%s expected, but was not thrown", expectedException.getSimpleName()));
    String message = exception.getMessage();
    assertTrue(message != null && !message.isEmpty(), "Exception message expected, but not found");
  }

  @Test
  public void stringStringCtor_InvalidRoman_ShouldThrowException() {
    String roman = "h";
    String arabic = "5";
    assertExceptionThrown(NumberFormatException.class, () -> new RomanInteger(roman, arabic));
  }

  @Test
  public void stringStringCtor_InvalidInteger_ShouldThrowException() {
    String roman = "MMMMM";
    String arabic = "5000";
    assertExceptionThrown(IllegalArgumentException.class, () -> new RomanInteger(roman, arabic));
  }

  @ParameterizedTest
  @MethodSource("copyConstructorTestsData")
  public void copyConstructorTests(RomanInteger source) {
    RomanInteger copy = new RomanInteger(source);
    assertNotSame(source, copy, "Expected different object, but was same");
    assertEquals(source.getArabic(), copy.getArabic(), "Arabic value not as expected");
    assertEquals(source.getRoman(), copy.getRoman(), "Roman value not as expected");
  }

  private static Stream<Arguments> copyConstructorTestsData() {
    return Stream.of(Arguments.of(RomanInteger.FIFTY), Arguments.of(RomanInteger.HUNDRED), Arguments.of(RomanInteger.THOUSAND));
  }
}
