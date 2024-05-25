package com.github.chaosfirebolt.converter.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxCalculatorTests {

  @ParameterizedTest
  @CsvSource({
          "1000, 3999",
          "10000, 39999",
          "1000000, 3999999"
  })
  public void testMax(int maxRegistered, int expectedMaxValue) {
    int actualMaxValue = MaxCalculator.calculateMax(maxRegistered);
    assertEquals(expectedMaxValue, actualMaxValue, "Incorrect calculated maximum value");
  }
}
