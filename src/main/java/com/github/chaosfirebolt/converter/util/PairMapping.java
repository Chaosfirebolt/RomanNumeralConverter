package com.github.chaosfirebolt.converter.util;

import java.util.Map;
import java.util.NavigableMap;

/**
 * The mapping of roman to arabic pairs.
 */
public sealed interface PairMapping permits PairMap {

  /**
   * Getter method for roman to arabic mapping.
   *
   * @return unmodifiable mapping.
   */
  Map<String, Integer> getRomanToArabic();

  /**
   * Getter method for arabic to roman mapping.
   *
   * @return unmodifiable mapping.
   */
  NavigableMap<Integer, String> getArabicToRoman();

  /**
   * Experimental feature. Extend the range of roman numerals.
   *
   * @param symbolNextOrderFive symbol for the next order of five based numeral
   * @param symbolNextOrderTen  symbol for the next order of ten based numeral
   * @throws IllegalArgumentException if the symbols are not ASCII letters or are already registered, or any other reason to consider them invalid
   */
  void registerNextOrder(char symbolNextOrderFive, char symbolNextOrderTen);

  /**
   * Experimental! Clears additional registered orders.
   */
  void clearAdditionalOrders();

  /**
   * Calculates the minimum value supported by the mappings.
   *
   * @return min value
   */
  int calculateMin();

  /**
   * Calculates the maximum value supported by the mappings.
   *
   * @return max value
   */
  int calculateMax();

  /**
   * Verifies that provided integer is in valid range for roman numerals.
   *
   * @param arabic integer to check.
   * @throws IllegalArgumentException if provided integer is not in valid range.
   */
  default void validateRange(int arabic) {
    int min = calculateMin();
    int max = calculateMax();
    if (arabic < min || arabic > max) {
      throw new IllegalArgumentException(String.format("Valid range for roman integers is from %d to %d inclusive.", min, max));
    }
  }
}
