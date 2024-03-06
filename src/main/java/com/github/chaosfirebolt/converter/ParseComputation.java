package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.constants.IntegerType;

import java.util.Locale;
import java.util.function.Function;

/**
 * Represents a computation to parse {@link RomanInteger} from provided input
 */
class ParseComputation implements Function<String, RomanInteger> {

  private final ParserCache parserCache;

  ParseComputation(ParserCache parserCache) {
    this.parserCache = parserCache;
  }

  /**
   * Parses provided input to {@link RomanInteger}.
   * Detects automatically input type and parses using appropriate {@link Parser}
   *
   * @param numeral numeral to be parsed
   * @return parsed RomanInteger
   * @throws NumberFormatException if provided numeral does not match any format
   */
  @Override
  public RomanInteger apply(String numeral) {
    String normalizedNumeral = numeral.trim().toUpperCase(Locale.ENGLISH);
    IntegerType integerType = IntegerType.fromNumeral(normalizedNumeral);
    Parser parser = this.parserCache.getValue(integerType);
    return parser.parse(numeral);
  }
}
