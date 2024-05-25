package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.RomanInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PairMapTests {

  @AfterEach
  public void cleanUp() {
    PairMap.getInstance().clearAdditionalOrders();
  }

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

  @Test
  public void registerNextOrder_ShouldThrowForSameSymbols() {
    char symbol = 'O';
    assertExceptionThrownWithMessage(() -> PairMap.getInstance().registerNextOrder(symbol, symbol));
  }

  private void assertExceptionThrownWithMessage(Executable executable) {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable, "Expected exception was not thrown");
    String message = exception.getMessage();
    assertTrue(message != null && !message.isBlank(), "Missing exception message");
  }

  @ParameterizedTest
  @ValueSource(chars = {'i', 'I'})
  public void fiveSymbolRegistered_ShouldThrow(char character) {
    assertExceptionThrownWithMessage(() -> PairMap.getInstance().registerNextOrder(character, 'H'));
  }

  @ParameterizedTest
  @ValueSource(chars = {'c', 'C'})
  public void tenSymbolRegistered_ShouldThrow(char character) {
    assertExceptionThrownWithMessage(() -> PairMap.getInstance().registerNextOrder('Y', character));
  }

  @ParameterizedTest
  @ValueSource(chars = {'*', '9', ',', 'ä', 'ö', 'ü', '\''})
  public void registerNotALetter_ShouldThrow(char character) {
    assertExceptionThrownWithMessage(() -> PairMap.getInstance().registerNextOrder('Y', character));
    assertExceptionThrownWithMessage(() -> PairMap.getInstance().registerNextOrder(character, 'Y'));
  }

  @Test
  public void registerValid_ShouldBeAddedForCorrectOrder() {
    char forFive = 'A';
    char forTen = 'h';
    PairMap.getInstance().registerNextOrder(forFive, forTen);

    assertMappings(5_000, forFive);
    assertMappings(10_000, forTen);
  }

  private void assertMappings(int expectedArabicValue, char character) {
    String expectedSymbol = Character.toString(character).toUpperCase(Locale.ENGLISH);
    assertEquals(expectedArabicValue, PairMap.getInstance().getRomanToArabic().get(expectedSymbol), "Incorrect roman to arabic mapping");
    assertEquals(expectedSymbol, PairMap.getInstance().getArabicToRoman().get(expectedArabicValue), "Incorrect arabic to roman mapping");
  }

  @Test
  public void clearAfterRegistration_DefaultMappingsShouldBePresent() {
    PairMap.getInstance().registerNextOrder('a', 's');
    PairMap.getInstance().clearAdditionalOrders();

    List<RomanInteger> basicNumerals = basicNumerals();
    assertEquals(basicNumerals.size(), PairMap.getInstance().getRomanToArabic().size(), "Incorrect roman to arabic mappings size");
    assertEquals(basicNumerals.size(), PairMap.getInstance().getArabicToRoman().size(), "Incorrect arabic to roman mappings size");

    for (RomanInteger basicNumeral : basicNumerals) {
      Supplier<String> messageSupplier = () -> "Incorrect mapping for - " + basicNumeral;
      Integer romanToArabicValue = PairMap.getInstance().getRomanToArabic().get(basicNumeral.getRoman());
      assertEquals(basicNumeral.getArabic(), romanToArabicValue, messageSupplier);

      String arabicToRomanValue = PairMap.getInstance().getArabicToRoman().get(basicNumeral.getArabic());
      assertEquals(basicNumeral.getRoman(), arabicToRomanValue, messageSupplier);
    }
  }

  @Test
  public void minimumValue_ShouldBeOne() {
    assertEquals(1, PairMap.getInstance().calculateMin(), "Incorrect minimum value");
  }

  @Test
  public void maximumValue_ShouldInvokeCalc() {
    try (MockedStatic<MaxCalculator> calculator = mockStatic(MaxCalculator.class)) {
      calculator.when(() -> MaxCalculator.calculateMax(anyInt())).thenCallRealMethod();
      PairMap.getInstance().calculateMax();
      calculator.verify(() -> MaxCalculator.calculateMax(anyInt()));
    }
  }

  @Test
  public void getPair_SymbolExists_ShouldReturnCorrect() {
    char symbol = 'X';
    Optional<Pair> result = PairMap.getInstance().getPair(symbol);
    assertTrue(result.isPresent(), "Expected pair not found");
    assertEquals(new Pair(symbol, 10), result.get(), "Incorrect pair found");
  }

  @Test
  public void getPair_SymbolDoesNotExist_ShouldReturnEmpty() {
    char symbol = 'R';
    Optional<Pair> result = PairMap.getInstance().getPair(symbol);
    assertTrue(result.isEmpty(), "Unexpected pair found");
  }

  @Test
  public void getPair_ArabicExists_ShouldReturnCorrect() {
    int arabic = 50;
    Optional<Pair> result = PairMap.getInstance().getPair(arabic);
    assertTrue(result.isPresent(), "Expected pair not found");
    assertEquals(new Pair('L', arabic), result.get(), "Incorrect pair found");
  }

  @Test
  public void getPair_ArabicDoesNotExist_ShouldReturnEmpty() {
    int arabic = 11;
    Optional<Pair> result = PairMap.getInstance().getPair(arabic);
    assertTrue(result.isEmpty(), "Unexpected pair found");
  }

  @Test
  public void higherPair_NoSuchPair_ShouldReturnEmpty() {
    Optional<Pair> result = PairMap.getInstance().getHigherPair(RomanInteger.THOUSAND.getArabic());
    assertTrue(result.isEmpty(), "Unexpected pair found");
  }

  @Test
  public void higherPair_ShouldReturnCorrect() {
    Optional<Pair> result = PairMap.getInstance().getHigherPair(RomanInteger.FIVE_HUNDRED.getArabic());
    assertTrue(result.isPresent(), "Expected pair not found");
    assertEquals(new Pair('M', 1000), result.get(), "Incorrect pair found");
  }

  @Test
  public void floorPair_NoSuchPair_ShouldReturnEmpty() {
    Optional<Pair> result = PairMap.getInstance().getFloorPair(0);
    assertTrue(result.isEmpty(), "Unexpected pair found");
  }

  @Test
  public void floorPair_ShouldReturnCorrect() {
    Optional<Pair> result = PairMap.getInstance().getFloorPair(RomanInteger.ONE.getArabic());
    assertTrue(result.isPresent(), "Expected pair not found");
    assertEquals(new Pair('I', 1), result.get(), "Incorrect pair found");
  }

  @Test
  public void count_ShouldBeCorrect() {
    PairMap pairMap = PairMap.getInstance();
    assertEquals(7, pairMap.count(), "Incorrect initial pair count");
    pairMap.registerNextOrder('a', 's');
    assertEquals(9, pairMap.count(), "Incorrect pair count after registering next order");
  }

  @Test
  public void pairs_ShouldBeCorrect() {
    PairMap pairMap = PairMap.getInstance();

    List<Pair> actualPairs = pairMap.pairs();
    List<Pair> expectedPairs = basicNumerals()
            .stream()
            .map(ri -> new Pair(ri.getRoman().charAt(0), ri.getArabic()))
            .collect(Collectors.toCollection(ArrayList::new));
    assertEquals(expectedPairs, actualPairs, "Incorrect pairs");

    pairMap.registerNextOrder('a', 's');
    actualPairs = pairMap.pairs();
    expectedPairs.add(new Pair('A', 5_000));
    expectedPairs.add(new Pair('S', 10_000));
    assertEquals(expectedPairs, actualPairs, "Incorrect pairs after registering next order");
  }
}
