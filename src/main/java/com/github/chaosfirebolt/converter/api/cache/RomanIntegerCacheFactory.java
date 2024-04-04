package com.github.chaosfirebolt.converter.api.cache;

import java.util.function.Function;

/**
 * Factory, responsible for creating instances of {@link RomanIntegerCache}.
 * Extends {@link Function} for backwards compatibility.
 *
 * @since 3.2.0
 */
@FunctionalInterface
public interface RomanIntegerCacheFactory extends Function<ParserCache, RomanIntegerCache> {

  /**
   * Creates an instance of {@link RomanIntegerCache}, using provided {@link ParserCache}.
   *
   * @param parserCache {@link ParserCache} to be used in the creation of the instance
   * @return a new instance of {@link RomanIntegerCache}
   * @since 3.2.0
   */
  default RomanIntegerCache createCache(ParserCache parserCache) {
    return apply(parserCache);
  }
}
