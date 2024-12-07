package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.DefaultParserCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCacheFactory;
import com.github.chaosfirebolt.converter.api.initialization.InitializationCapable;
import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.util.PairMap;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

/**
 * This class represents roman numerals.
 * Provides public constructors, constants for the seven basic roman numerals
 * and static methods for parsing.
 * Comparison is done via arabic representation for this numeral(int).
 * RomanInteger objects are immutable.
 */
public final class RomanInteger implements Comparable<RomanInteger>, Cloneable, Serializable {

  @Serial
  private static final long serialVersionUID = 2L;

  private static final ParserCache PARSER_CACHE = new DefaultParserCache();

  private static RomanIntegerCache valueCache = new NoOpRomanIntegerCache(PARSER_CACHE);

  /**
   * Comparator defining the natural ordering of roman integers.
   * @deprecated As of release 3.6.0, replaced by {@link #naturalOrder()}.
   */
  @Deprecated(since = "3.6.0", forRemoval = true)
  public static final Comparator<RomanInteger> NATURAL_ORDER_COMPARATOR = Comparator.comparingInt(RomanInteger::getArabic);

  /**
   * Constant representing arabic number "1", roman numeral - "I".
   */
  public static final RomanInteger ONE = new RomanInteger("I", 1);
  /**
   * Constant representing arabic number "5", roman numeral - "V".
   */
  public static final RomanInteger FIVE = new RomanInteger("V", 5);
  /**
   * Constant representing arabic number "10", roman numeral - "X".
   */
  public static final RomanInteger TEN = new RomanInteger("X", 10);
  /**
   * Constant representing arabic number "50", roman numeral - "L".
   */
  public static final RomanInteger FIFTY = new RomanInteger("L", 50);
  /**
   * Constant representing arabic number "100", roman numeral - "C".
   */
  public static final RomanInteger HUNDRED = new RomanInteger("C", 100);
  /**
   * Constant representing arabic number "500", roman numeral - "D".
   */
  public static final RomanInteger FIVE_HUNDRED = new RomanInteger("D", 500);
  /**
   * Constant representing arabic number "1000", roman numeral - "M".
   */
  public static final RomanInteger THOUSAND = new RomanInteger("M", 1000);

  /**
   * Roman numeral representation for this RomanInteger object.
   * Null values are not permitted.
   */
  private final String romanRepresentation;
  /**
   * Arabic number representing this roman numeral
   */
  private final int arabicRepresentation;

  /**
   * Initializes new {@link RomanInteger} object with provided roman string and arabic integer.
   * For internal usage only.
   *
   * @param romanRepresentation  string representing roman numeral.
   * @param arabicRepresentation integer representing arabic value for provided roman numeral.
   */
  RomanInteger(String romanRepresentation, int arabicRepresentation) {
    this.romanRepresentation = romanRepresentation;
    this.arabicRepresentation = arabicRepresentation;
  }

  /**
   * Initializes new {@link RomanInteger} object with provided roman and arabic strings.
   * Throws exception if either argument is invalid.
   *
   * @param romanRepresentation  string representing roman numeral.
   * @param arabicRepresentation string representing arabic value for provided roman numeral.
   * @throws NullPointerException     if either argument is null.
   * @throws NumberFormatException    if provided roman and arabic numerals do not match required format.
   * @throws IllegalArgumentException if provided arabic and roman numerals do not represent same value
   *                                  or arabic number is not in valid range.
   */
  public RomanInteger(String romanRepresentation, String arabicRepresentation) {
    this(romanRepresentation, validate(romanRepresentation, Integer.parseInt(Objects.requireNonNull(arabicRepresentation))));
  }

  private static int validate(String romanRepresentation, int arabicRepresentation) {
    Parser romanParser = PARSER_CACHE.getValue(IntegerType.ROMAN);
    RomanInteger parsed = romanParser.parse(Objects.requireNonNull(romanRepresentation));
    if (parsed.arabicRepresentation != arabicRepresentation) {
      throw new IllegalArgumentException("Roman numeral must represent same value as provided arabic representation.");
    }
    return arabicRepresentation;
  }

  /**
   * Copy constructor, initializes new {@link RomanInteger} object, representing the same roman integer, from provided {@link RomanInteger}.
   *
   * @param romanInteger roman integer to be copied.
   */
  public RomanInteger(RomanInteger romanInteger) {
    this(romanInteger.romanRepresentation, romanInteger.arabicRepresentation);
  }

  /**
   * Comparator defining the natural ordering of roman integers.
   *
   * @return the natural order comparator
   */
  public static Comparator<RomanInteger> naturalOrder() {
    return NATURAL_ORDER_COMPARATOR;
  }

  /**
   * Experimental feature! Extends the range of roman integers by registering symbols for the next order.
   * Throws exception, if the symbols are considered invalid for any reason.
   *
   * @param symbolNextOrderFive symbol for the next order of five based numeral
   * @param symbolNextOrderTen symbol for the next order of ten based numeral
   * @throws IllegalArgumentException if the symbols are not ASCII letters or are already registered, or any other reason to consider them invalid
   */
  public static void extend(char symbolNextOrderFive, char symbolNextOrderTen) {
    PairMap.getInstance().registerNextOrder(symbolNextOrderFive, symbolNextOrderTen);
  }

  /**
   * Removes all registered extensions of the roman numeral range, leaving only the basic symbols.
   */
  public static void clearExtensions() {
    PairMap.getInstance().clearAdditionalOrders();
  }

  /**
   * Enables caching of parsed roman integers.
   */
  public static void enableCache() {
    setCache(UniDirectionalRomanIntegerCache::new);
  }

  /**
   * Disable caching of parsed roman integers.
   * This is the default state.
   */
  public static void disableCache() {
    setCache(NoOpRomanIntegerCache::new);
  }

  /**
   * Sets the cache with custom implementation.
   * It is recommended, but not required, that the factory uses provided parser cache.
   * New cache will be initialized and previous one will be cleared, if they support the operations.
   * Deprecated in favour of {@link #setCache(RomanIntegerCacheFactory)}.
   *
   * @param cacheFactory factory responsible for creating the cache
   * @deprecated As of release 3.2.0, replaced by {@link #setCache(RomanIntegerCacheFactory)}
   */
  @Deprecated(since = "3.2.0", forRemoval = true)
  public static void setCache(Function<ParserCache, RomanIntegerCache> cacheFactory) {
    RomanIntegerCache newCache = cacheFactory.apply(PARSER_CACHE);
    handleSetCache(newCache);
  }

  private static void handleSetCache(RomanIntegerCache newCache) {
    if (newCache instanceof InitializationCapable initializationCapableCache) {
      initializationCapableCache.initialize();
    }
    RomanIntegerCache oldCache = valueCache;
    valueCache = newCache;
    oldCache.clear();
  }

  /**
   * Sets the cache with custom implementation.
   * It is recommended, but not required, that the factory uses provided parser cache.
   * New cache will be initialized and previous one will be cleared, if they support the operations.
   *
   * @param cacheFactory factory responsible for creating the cache
   * @since 3.2.0
   */
  public static void setCache(RomanIntegerCacheFactory cacheFactory) {
    RomanIntegerCache newCache = cacheFactory.createCache(PARSER_CACHE);
    handleSetCache(newCache);
  }

  /**
   * Parses provided string to a RomanInteger object.
   * Convenience method resolving by itself the integer type of provided string and which parser should be used.
   * Throws exceptions in case of invalid input.
   *
   * @param number string to parse.
   * @return {@link RomanInteger} object in case of valid input.
   * @throws NumberFormatException    if provided string does not match roman or arabic formats.
   * @throws IllegalArgumentException if arabic representation is not in valid range.
   * @throws NullPointerException     if argument is null.
   */
  public static RomanInteger parse(String number) {
    return valueCache.getValue(Objects.requireNonNull(number, "Number to parse was 'null'"));
  }

  /**
   * Getter for arabic representation.
   *
   * @return arabic number representing this RomanInteger.
   */
  public int getArabic() {
    return arabicRepresentation;
  }

  /**
   * Getter for roman representation.
   *
   * @return roman number representing this RomanInteger.
   */
  public String getRoman() {
    return romanRepresentation;
  }

  @Override
  public int hashCode() {
    return Objects.hash(arabicRepresentation, romanRepresentation);
  }

  /**
   * Tests this object and provided object for equality.
   * This object is considered equal to provided object, if argument is
   * instance of {@link RomanInteger}, their arabic representations are equal and their roman representations are equal.
   * E.g. instance holding roman - "XVIII" and arabic - 18 is <strong>not</strong> equal to instance holding roman - "IIXX" and arabic - 18,
   * because the roman numerals are not the same, even though both represent same arabic value - 18.
   *
   * @param obj object to test against for equality.
   * @return {@code true} if objects are equal, {@code false} otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RomanInteger other)) {
      return false;
    }
    return Objects.equals(arabicRepresentation, other.arabicRepresentation) && Objects.equals(romanRepresentation, other.romanRepresentation);
  }

  @Override
  public String toString() {
    return arabicRepresentation + " - " + romanRepresentation;
  }

  /**
   * Compares numerically this and provided {@link RomanInteger} objects.
   * This implementation is <strong>NOT</strong> consistent with {@link RomanInteger#equals(Object)}.
   * When comparing an instance holding arabic 18 and roman "XVIII" and an instance holding arabic 18 and roman "IIXX",
   * the natural ordering will consider them equal, returning {@code 0}, while {@link #equals(Object)} would not, and return {@code false}.
   *
   * @param other another {@link RomanInteger}
   * @return {@code -1} if this {@link #arabicRepresentation} is numerically less than
   * other {@link #arabicRepresentation}, {@code 0} if arabic representations are equal
   * and {@code 1} this arabic number is numerically grater than other arabic number.
   * Method returns {@code 0} only if {@link #equals(Object)} would return {@code true}.
   */
  @Override
  public int compareTo(RomanInteger other) {
    return naturalOrder().compare(this, other);
  }

  @Override
  public RomanInteger clone() {
    try {
      return (RomanInteger) super.clone();
    } catch (CloneNotSupportedException exc) {
      //should never happen
      return new RomanInteger(this);
    }
  }
}
