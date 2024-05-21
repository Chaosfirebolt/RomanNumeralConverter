package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.RomanInteger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * Singleton holding mappings from arabic to roman, and roman to arabic basic numerals.
 */
public final class PairMap implements PairMapping {

  /**
   * Instance of the class.
   */
  private static PairMap instance;

  /**
   * Roman to arabic unmodifiable mapping.
   */
  private final Map<String, Integer> romanToArabic;
  /**
   * Arabic to roman unmodifiable mapping.
   */
  private final NavigableMap<Integer, String> arabicToRoman;

  private PairMap(Map<String, Integer> romanToArabic, NavigableMap<Integer, String> arabicToRoman) {
    this.romanToArabic = romanToArabic;
    this.arabicToRoman = arabicToRoman;
  }

  /**
   * Getter for instance.
   *
   * @return single instance of the class.
   */
  //TODO instantiate the static instance variable directly
  public static PairMap getInstance() {
    if (instance == null) {
      Map<String, Integer> toArab = new HashMap<>();
      NavigableMap<Integer, String> toRoman = new TreeMap<>();

      add(toArab, toRoman, RomanInteger.ONE);
      add(toArab, toRoman, RomanInteger.FIVE);
      add(toArab, toRoman, RomanInteger.TEN);
      add(toArab, toRoman, RomanInteger.FIFTY);
      add(toArab, toRoman, RomanInteger.HUNDRED);
      add(toArab, toRoman, RomanInteger.FIVE_HUNDRED);
      add(toArab, toRoman, RomanInteger.THOUSAND);
      instance = new PairMap(toArab, toRoman);
    }
    return instance;
  }

  private static void add(Map<String, Integer> toArab, NavigableMap<Integer, String> toRoman, RomanInteger romanInteger) {
    add(toArab, toRoman, romanInteger.getArabic(), romanInteger.getRoman());
  }

  private static void add(Map<String, Integer> toArab, NavigableMap<Integer, String> toRoman, int arabic, String roman) {
    toArab.put(roman, arabic);
    toRoman.put(arabic, roman);
  }

  @Override
  public Map<String, Integer> getRomanToArabic() {
    return Collections.unmodifiableMap(romanToArabic);
  }

  @Override
  public NavigableMap<Integer, String> getArabicToRoman() {
    return Collections.unmodifiableNavigableMap(arabicToRoman);
  }

  @Override
  public void registerNextOrder(char symbolNextOrderFive, char symbolNextOrderTen) {
    if (symbolNextOrderFive == symbolNextOrderTen) {
      throw new IllegalArgumentException("Duplicate symbols");
    }
    String fiveString = Character.toString(symbolNextOrderFive).toUpperCase(Locale.ENGLISH);
    if (isNotLetter(symbolNextOrderFive) || romanToArabic.containsKey(fiveString)) {
      throw new IllegalArgumentException(buildExceptionMessage(fiveString));
    }
    String tenString = Character.toString(symbolNextOrderTen).toUpperCase(Locale.ENGLISH);
    if (isNotLetter(symbolNextOrderTen) || romanToArabic.containsKey(tenString)) {
      throw new IllegalArgumentException(buildExceptionMessage(tenString));
    }

    int forTen = arabicToRoman.lastKey() * 10;
    int forFive = forTen / 2;

    add(romanToArabic, arabicToRoman, forTen, tenString);
    add(romanToArabic, arabicToRoman, forFive, fiveString);
  }

  private static boolean isNotLetter(char ch) {
    return (ch < 'A' || ch > 'Z') && (ch < 'a' || ch > 'z');
  }

  private static String buildExceptionMessage(String existingSymbol) {
    return String.format("Symbol '%s' is already registered", existingSymbol);
  }

  @Override
  public void clearAdditionalOrders() {
    clearMap(romanToArabic, entry -> entry.getValue() > RomanInteger.THOUSAND.getArabic());
    clearMap(arabicToRoman, entry -> entry.getKey() > RomanInteger.THOUSAND.getArabic());
  }

  private <K, V> void clearMap(Map<K, V> map, Predicate<Map.Entry<K, V>> removeCondition) {
    map.entrySet().removeIf(removeCondition);
  }

  @Override
  public int calculateMin() {
    return 1;
  }

  @Override
  public int calculateMax() {
    return MaxCalculator.calculateMax(arabicToRoman.lastKey());
  }

  @Override
  public Optional<Pair> getPair(String roman) {
    return fromValues(roman, romanToArabic.get(roman));
  }

  private static Optional<Pair> fromValues(String roman, Integer arabic) {
    if (roman == null || arabic == null) {
      return Optional.empty();
    }
    return Optional.of(new Pair(roman, arabic));
  }

  @Override
  public Optional<Pair> getPair(Integer arabic) {
    return fromValues(arabicToRoman.get(arabic), arabic);
  }

  @Override
  public Optional<Pair> getHigherPair(Integer arabic) {
    return pairFromEntry(arabicToRoman.higherEntry(arabic));
  }

  private static Optional<Pair> pairFromEntry(Map.Entry<Integer, String> entry) {
    if (entry == null) {
      return Optional.empty();
    }
    return Optional.of(new Pair(entry.getValue(), entry.getKey()));
  }

  @Override
  public Optional<Pair> getFloorPair(Integer arabic) {
    return pairFromEntry(arabicToRoman.floorEntry(arabic));
  }
}
