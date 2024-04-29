package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.storage.Computation;
import com.github.chaosfirebolt.converter.constants.IntegerType;

import java.util.Locale;
import java.util.function.Function;

final class DelegatingParser implements Parser, Function<String, RomanInteger>, Computation<String, RomanInteger> {

  private final ParserCache parserCache;

  DelegatingParser(ParserCache parserCache) {
    this.parserCache = parserCache;
  }

  @Override
  public RomanInteger parse(String numeral) {
    String normalizedNumeral = numeral.trim().toUpperCase(Locale.ENGLISH);
    IntegerType integerType = IntegerType.fromNumeral(normalizedNumeral);
    Parser parser = parserCache.getValue(integerType);
    return parser.parse(numeral);
  }

  @Override
  public RomanInteger apply(String input) {
    return parse(input);
  }

  @Override
  public RomanInteger compute(String input) {
    return parse(input);
  }

  @Override
  public Function<String, RomanInteger> unwrap() {
    return this;
  }
}
