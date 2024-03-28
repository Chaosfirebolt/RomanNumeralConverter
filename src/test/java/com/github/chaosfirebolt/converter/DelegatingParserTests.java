package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.DefaultParserCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.constants.IntegerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class DelegatingParserTests {

  private ParserCache parserCache;
  private DelegatingParser parser;

  @BeforeEach
  public void setup() {
    this.parserCache = spy(new DefaultParserCache());
    this.parser = spy(new DelegatingParser(parserCache));
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/caching.csv", numLinesToSkip = 1)
  public void parseFromArabic(String arabic, String roman) {
    RomanInteger arabicParseResult = parser.parse(arabic);
    final String arabicParseMessage = "Incorrect parsing from arabic numeral";
    assertEquals(roman, arabicParseResult.getRoman(), arabicParseMessage);
    assertEquals(Integer.parseInt(arabic), arabicParseResult.getArabic(), arabicParseMessage);

    verify(parserCache).getValue(IntegerType.ARABIC);
    verify(parserCache, never()).getValue(IntegerType.ROMAN);
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/caching.csv", numLinesToSkip = 1)
  public void parseFromRoman(String arabic, String roman) {
    RomanInteger romanParseResult = parser.parse(roman);
    final String romanParseMessage = "Incorrect parsing from roman numeral";
    assertEquals(roman, romanParseResult.getRoman(), romanParseMessage);
    assertEquals(Integer.parseInt(arabic), romanParseResult.getArabic(), romanParseMessage);

    verify(parserCache).getValue(IntegerType.ROMAN);
    verify(parserCache, never()).getValue(IntegerType.ARABIC);
  }

  public static List<String> values() {
    return List.of("1", "11", "XXIV", "1259", "MC", "111", "MD", "LX", "321");
  }

  @ParameterizedTest
  @MethodSource("values")
  public void applyShouldInvokeParse(String value) {
    parser.apply(value);
    verify(parser).parse(value);
    verify(parser, never()).compute(any());
  }

  @ParameterizedTest
  @MethodSource("values")
  public void computeShouldInvokeParse(String value) {
    parser.compute(value);
    verify(parser).parse(value);
    verify(parser, never()).apply(any());
  }

  @Test
  public void unwrapShouldReturnSameInstance() {
    Function<String, RomanInteger> unwrapped = parser.unwrap();
    assertSame(parser, unwrapped, "Unwrapping delegating parser should return same instance");
  }
}
