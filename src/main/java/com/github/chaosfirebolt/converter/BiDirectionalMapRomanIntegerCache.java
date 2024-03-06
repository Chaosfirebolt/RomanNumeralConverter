package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.ParserCache;

import java.util.Map;
import java.util.function.Function;

/**
 * Caches result in both directions - arabic and roman representations are mapped to the same {@link RomanInteger} instance.
 */
public class BiDirectionalMapRomanIntegerCache extends MapRomanIntegerCache {

  /**
   * Constructs an instance of Map based bidirectional roman integer cache
   *
   * @param parserCache parsers cache
   */
  public BiDirectionalMapRomanIntegerCache(ParserCache parserCache) {
    super(parserCache);
  }

  @Override
  protected RomanInteger cacheIfAbsent(Map<String, RomanInteger> cache, String key, Function<String, RomanInteger> computation) {
    RomanInteger cached = cache.get(key);
    if (cached != null) {
      return cached;
    }
    RomanInteger computed = computation.apply(key);
    cache.putIfAbsent(computed.getRoman(), computed);
    cache.putIfAbsent(Integer.toString(computed.getArabic()), computed);
    return computed;
  }
}
