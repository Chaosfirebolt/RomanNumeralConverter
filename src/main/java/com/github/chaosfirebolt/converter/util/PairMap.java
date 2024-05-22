package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.initialization.source.BasicNumeralsInputSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

  private static final PairMap INSTANCE = new PairMap();

  /**
   * Roman to arabic unmodifiable mapping.
   */
  private final Map<String, Integer> romanToArabic;
  /**
   * Arabic to roman unmodifiable mapping.
   */
  private final NavigableMap<Integer, String> arabicToRoman;

  private PairMap() {
    this.romanToArabic = new HashMap<>();
    this.arabicToRoman = new TreeMap<>();
    initData();
  }

  private void initData() {
    RomanInteger[] basicNumerals = new BasicNumeralsInputSource().getInputData();
    for (RomanInteger romanInteger : basicNumerals) {
      add(romanInteger.getRoman(), romanInteger.getArabic());
    }
  }

  private void add(String roman, int arabic) {
    romanToArabic.put(roman, arabic);
    arabicToRoman.put(arabic, roman);
  }

  /**
   * Getter for instance.
   *
   * @return single instance of the class.
   */
  public static PairMap getInstance() {
    return INSTANCE;
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
    String romanSymbolFive = Character.toString(symbolNextOrderFive).toUpperCase(Locale.ENGLISH);
    if (isNotLetter(symbolNextOrderFive) || romanToArabic.containsKey(romanSymbolFive)) {
      throw new IllegalArgumentException(buildExceptionMessage(romanSymbolFive));
    }
    String romanSymbolTen = Character.toString(symbolNextOrderTen).toUpperCase(Locale.ENGLISH);
    if (isNotLetter(symbolNextOrderTen) || romanToArabic.containsKey(romanSymbolTen)) {
      throw new IllegalArgumentException(buildExceptionMessage(romanSymbolTen));
    }

    int arabicValueTen = arabicToRoman.lastKey() * 10;
    int arabicValueFive = arabicValueTen / 2;

    add(romanSymbolTen, arabicValueTen);
    add(romanSymbolFive, arabicValueFive);
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
    Integer arabic = romanToArabic.get(roman);
    if (arabic == null) {
      return Optional.empty();
    }
    return Optional.of(new Pair(roman, arabic));
  }

  @Override
  public Optional<Pair> getPair(int arabic) {
    String roman = arabicToRoman.get(arabic);
    if (roman == null) {
      return Optional.empty();
    }
    return Optional.of(new Pair(roman, arabic));
  }

  @Override
  public Optional<Pair> getHigherPair(int arabic) {
    return pairFromEntry(arabicToRoman.higherEntry(arabic));
  }

  private static Optional<Pair> pairFromEntry(Map.Entry<Integer, String> entry) {
    if (entry == null) {
      return Optional.empty();
    }
    return Optional.of(new Pair(entry.getValue(), entry.getKey()));
  }

  @Override
  public Optional<Pair> getFloorPair(int arabic) {
    return pairFromEntry(arabicToRoman.floorEntry(arabic));
  }

  @Override
  public int count() {
    return romanToArabic.size();
  }

  @Override
  public List<Pair> pairs() {
    return romanToArabic.entrySet()
            .stream()
            .map(entry -> new Pair(entry.getKey(), entry.getValue()))
            .toList();
  }
}
