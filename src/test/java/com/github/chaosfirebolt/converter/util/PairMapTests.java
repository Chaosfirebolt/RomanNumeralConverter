package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.RomanInteger;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class PairMapTests {

  @ParameterizedTest
  @MethodSource("immutableMaps")
  public void returnedMapsShouldNotAllowRemovingOperations(Map<?, ?> map) {
    assertUnmodifiable(map::clear, "clear");
  }

  private static List<Map<?, ?>> immutableMaps() {
    return List.of(
            PairMap.getInstance().getArabicToRoman(),
            PairMap.getInstance().getRomanToArabic()
    );
  }

  private void assertUnmodifiable(Executable executable, String operationName) {
    Supplier<String> messageFactory = () -> String.format("Map is modifiable, operation '%s' did not throw an UnsupportedOperationException", operationName);
    assertThrows(UnsupportedOperationException.class, executable, messageFactory);
  }

  @ParameterizedTest
  @MethodSource("immutableMaps")
  public void returnedMapsShouldNotAllowAddingOperations(Map<?, ?> map) {
    assertUnmodifiable(() -> map.putAll(Collections.emptyMap()), "putAll");
  }

  @ParameterizedTest
  @MethodSource("basicNumerals")
  public void arabicToRomanContainsBasicNumeralMapping(RomanInteger romanInteger) {
    String actualRoman = PairMap.getInstance().getArabicToRoman().get(romanInteger.getArabic());
    assertNotNull(actualRoman, () -> "Missing mapping for - " + romanInteger);
    assertEquals(romanInteger.getRoman(), actualRoman, () -> "Wrong mapping for - " + romanInteger);
  }

  private static List<RomanInteger> basicNumerals() {
    return List.of(RomanInteger.ONE, RomanInteger.FIVE, RomanInteger.TEN, RomanInteger.FIFTY, RomanInteger.HUNDRED, RomanInteger.FIVE_HUNDRED, RomanInteger.THOUSAND);
  }

  @ParameterizedTest
  @MethodSource("basicNumerals")
  public void romanToArabicContainsBasicNumeralMapping(RomanInteger romanInteger) {
    Integer actualArabic = PairMap.getInstance().getRomanToArabic().get(romanInteger.getRoman());
    assertNotNull(actualArabic, () -> "Missing mapping for - " + romanInteger);
    assertEquals(romanInteger.getArabic(), actualArabic.intValue(), () -> "Wrong mapping for - " + romanInteger);
  }
}
