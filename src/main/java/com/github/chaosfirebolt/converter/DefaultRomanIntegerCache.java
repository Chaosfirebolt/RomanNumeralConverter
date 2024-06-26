package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.OperationFailure;
import com.github.chaosfirebolt.converter.api.cache.DefaultCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;
import com.github.chaosfirebolt.converter.api.cache.storage.MapStorage;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;
import com.github.chaosfirebolt.converter.api.initialization.InitializationData;
import com.github.chaosfirebolt.converter.api.initialization.RomanIntegerArrayInitializationData;
import com.github.chaosfirebolt.converter.api.initialization.source.BasicNumeralsInputSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class for customizing roman integer cache implementations.
 *
 * @since 3.3.0
 */
public class DefaultRomanIntegerCache extends DefaultCache<String, RomanInteger> implements RomanIntegerCache {

  /**
   * @param parserCache cache for parser instances
   */
  public DefaultRomanIntegerCache(ParserCache parserCache) {
    this(parserCache, MapStorage.lenient(HashMap::new), new RomanIntegerArrayInitializationData(new BasicNumeralsInputSource()));
  }

  /**
   * @param parserCache        cache for parser instances
   * @param storage            storage for key to computed value
   * @param initializationData data to pre initialize the cache with
   */
  public DefaultRomanIntegerCache(ParserCache parserCache, Storage<String, RomanInteger> storage, InitializationData<Map<String, RomanInteger>> initializationData) {
    super(storage, new DelegatingParser(parserCache), initializationData, () -> new OperationFailure("Caching failure"));
  }
}
