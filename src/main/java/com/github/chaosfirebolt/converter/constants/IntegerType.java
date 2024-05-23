package com.github.chaosfirebolt.converter.constants;

import com.github.chaosfirebolt.converter.ArabicParser;
import com.github.chaosfirebolt.converter.Parser;
import com.github.chaosfirebolt.converter.RomanParser;
import com.github.chaosfirebolt.converter.api.property.ConverterProperties;
import com.github.chaosfirebolt.converter.util.PairMap;

import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Enumeration for supported integer types.
 */
public enum IntegerType {

  /**
   * Representation for arabic format integers.
   */
  ARABIC(ArabicParser::new, new ArabicIntegerTypePatternFactory()),
  /**
   * Representation for roman format integers.
   */
  ROMAN(RomanParser::new, getRomanPatternFactory());

  private static final IntegerType[] VALUES = IntegerType.values();

  /**
   * Supplier for instances of parsers.
   */
  private final Supplier<Parser> parserFactory;
  /**
   * The pattern that matches this integer type
   */
  private final Supplier<Pattern> typePatternFactory;

  IntegerType(Supplier<Parser> parserFactory, Supplier<Pattern> typePatternFactory) {
    this.parserFactory = parserFactory;
    this.typePatternFactory = typePatternFactory;
  }

  private static Supplier<Pattern> getRomanPatternFactory() {
    Supplier<Pattern> patternFactory = new ResultCachingSupplier<>(new RomanIntegerTypePatternFactory(), new RecalculateByPairCount());
    boolean shouldSynchronize = ConverterProperties.getSyncProperty();
    return shouldSynchronize ? new SynchronizedSupplier<>(patternFactory) : patternFactory;
  }

  /**
   * Parses integer type from provided numeral
   *
   * @param numeral the numeral to match against
   * @return the type
   * @throws NumberFormatException if provided numeral does not match any format
   */
  public static IntegerType fromNumeral(String numeral) {
    for (IntegerType integerType : VALUES) {
      if (integerType.typePatternFactory.get().matcher(numeral).find()) {
        return integerType;
      }
    }
    throw new NumberFormatException("Numeral does not match any required format: " + numeral);
  }

  /**
   * Gets the corresponding parser for this integer type.
   *
   * @return the parser
   */
  public Parser getParser() {
    return parserFactory.get();
  }

  /**
   * Verifies that provided string matches the pattern for this type.
   *
   * @param representation string to verify.
   * @return provided string if it matches pattern.
   * @throws NumberFormatException if provided string does not match pattern.
   */
  public String validateFormat(String representation) {
    if (!typePatternFactory.get().matcher(representation).find()) {
      throw new NumberFormatException("Numeral does not match required format for string: " + representation);
    }
    return representation;
  }

  /**
   * Verifies that provided integer is in valid range for roman numerals.
   *
   * @param arabic integer to check.
   * @return provided integer, if it is in valid range.
   * @throws IllegalArgumentException if provided integer is not in valid range.
   */
  public int validateRange(int arabic) {
    PairMap.getInstance().validateRange(arabic);
    return arabic;
  }
}
