package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;
import com.github.chaosfirebolt.converter.api.initialization.InitializationData;

import java.util.Map;

/**
 * Cache saving computed roman integers in single computational direction.
 *
 * @since 3.3.0
 */
public final class UniDirectionalRomanIntegerCache extends DefaultRomanIntegerCache {

  /**
   * @param parserCache cache for parser instances
   */
  public UniDirectionalRomanIntegerCache(ParserCache parserCache) {
    super(parserCache);
  }

  /**
   * @param parserCache        cache for parser instances
   * @param storage            storage for key to computed value
   * @param initializationData data to pre initialize the cache with
   */
  public UniDirectionalRomanIntegerCache(ParserCache parserCache, Storage<String, RomanInteger> storage, InitializationData<Map<String, RomanInteger>> initializationData) {
    super(parserCache, storage, initializationData);
  }
}
