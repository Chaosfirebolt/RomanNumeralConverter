package com.github.chaosfirebolt.converter.exec.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IOTypeTests {

  @Test
  public void nullOptionShouldReturnConsole() {
    IOType result = IOType.fromOption(null);
    assertEquals(IOType.CONSOLE, result, "Null option should be parsed to console");
  }

  @ParameterizedTest
  @MethodSource
  public void validOptionShouldParseCorrectly(String option, IOType expected) {
    IOType actual = IOType.fromOption(option);
    assertEquals(expected, actual, "Option not parsed correctly");
  }

  private static List<Arguments> validOptionShouldParseCorrectly() {
    return List.of(
            Arguments.of("c", IOType.CONSOLE),
            Arguments.of("f", IOType.FILE),
            Arguments.of("F", IOType.FILE),
            Arguments.of("C", IOType.CONSOLE)
    );
  }

  @ParameterizedTest
  @ValueSource(strings = {"a", "s", "G", "asd", "qweRTY"})
  public void invalidOptionShouldThrowException(String option) {
    assertThrows(IllegalArgumentException.class, () -> IOType.fromOption(option), "Invalid option should have thrown exception");
  }
}
