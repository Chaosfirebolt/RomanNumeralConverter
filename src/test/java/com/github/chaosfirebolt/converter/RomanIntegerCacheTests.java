package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.MapParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class RomanIntegerCacheTests {

  @ParameterizedTest
  @CsvFileSource(resources = "/caching.csv", numLinesToSkip = 1)
  public void noOpCache_ShouldReturnDifferentInstances(String arabic, String roman) {
    RomanIntegerCache cache = new NoOpRomanIntegerCache(new MapParserCache());
    assertInstancesNotSame(cache, arabic);
    assertInstancesNotSame(cache, roman);
  }

  private static void assertInstancesNotSame(RomanIntegerCache cache, String value) {
    RomanInteger first = cache.getValue(value);
    RomanInteger second = cache.getValue(value);
    assertNotSame(first, second, "Expected different instances, but were same");
  }

  @ParameterizedTest
  @CsvFileSource(resources = "/caching.csv", numLinesToSkip = 1)
  public void mapCache_ShouldReturnSameInstances(String arabic, String roman) {
    RomanIntegerCache cache = new MapRomanIntegerCache(new MapParserCache());
    assertInstancesAreSame(cache, arabic);
    assertInstancesAreSame(cache, roman);
  }

  private static void assertInstancesAreSame(RomanIntegerCache cache, String value) {
    RomanInteger first = cache.getValue(value);
    RomanInteger second = cache.getValue(value);
    assertSame(first, second, "Expected same instances, but were different");
  }
}
