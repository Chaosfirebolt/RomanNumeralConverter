package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.initialization.source.BasicNumeralsInputSource;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Singleton holding mappings from arabic to roman, and roman to arabic basic numerals.
 */
public final class PairMap implements PairMapping {

  private static final PairMap INSTANCE = new PairMap();

  /**
   * Roman to arabic unmodifiable mapping.
   */
  private final Map<Character, Integer> romanToArabic;
  /**
   * Arabic to roman unmodifiable mapping.
   */
  private final NavigableMap<Integer, Character> arabicToRoman;

  private PairMap() {
    this.romanToArabic = new HashMap<>();
    this.arabicToRoman = new TreeMap<>();
    initData();
  }

  private void initData() {
    RomanInteger[] basicNumerals = new BasicNumeralsInputSource().getInputData();
    for (RomanInteger romanInteger : basicNumerals) {
      add(romanInteger.getRoman().charAt(0), romanInteger.getArabic());
    }
  }

  private void add(char roman, int arabic) {
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
    return romanToArabic.entrySet()
            .stream()
            .map(entry -> new AbstractMap.SimpleEntry<>(Character.toString(entry.getKey()), entry.getValue()))
            .collect(Collectors.collectingAndThen(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue), Collections::unmodifiableMap));
  }

  @Override
  public NavigableMap<Integer, String> getArabicToRoman() {
    return arabicToRoman.entrySet()
            .stream()
            .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), Character.toString(entry.getValue())))
            .collect(Collectors.collectingAndThen(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> {
              throw new IllegalStateException("Unexpected duplicate");
            }, TreeMap::new), Collections::unmodifiableNavigableMap));
  }

  @Override
  public void registerNextOrder(char symbolNextOrderFive, char symbolNextOrderTen) {
    if (symbolNextOrderFive == symbolNextOrderTen) {
      throw new IllegalArgumentException("Duplicate symbols");
    }
    char romanSymbolFive = Character.toUpperCase(symbolNextOrderFive);
    if (isNotLetter(symbolNextOrderFive) || romanToArabic.containsKey(romanSymbolFive)) {
      throw new IllegalArgumentException(buildExceptionMessage(romanSymbolFive));
    }
    char romanSymbolTen = Character.toUpperCase(symbolNextOrderTen);
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

  private static String buildExceptionMessage(char existingSymbol) {
    return String.format("Symbol '%s' is already registered", existingSymbol);
  }

  @Override
  public void clearAdditionalOrders() {
    int defaultMaxArabic = RomanInteger.THOUSAND.getArabic();
    clearMap(romanToArabic, entry -> entry.getValue() > defaultMaxArabic);
    clearMap(arabicToRoman, entry -> entry.getKey() > defaultMaxArabic);
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
  public Optional<Pair> getPair(char roman) {
    Integer arabic = romanToArabic.get(roman);
    if (arabic == null) {
      return Optional.empty();
    }
    return Optional.of(new Pair(roman, arabic));
  }

  @Override
  public Optional<Pair> getPair(int arabic) {
    Character roman = arabicToRoman.get(arabic);
    if (roman == null) {
      return Optional.empty();
    }
    return Optional.of(new Pair(roman, arabic));
  }

  @Override
  public Optional<Pair> getHigherPair(int arabic) {
    return pairFromEntry(arabicToRoman.higherEntry(arabic));
  }

  private static Optional<Pair> pairFromEntry(Map.Entry<Integer, Character> entry) {
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
            .sorted(Comparator.comparingInt(Pair::arabic))
            .toList();
  }
}
