package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.InvalidResultException;
import com.github.chaosfirebolt.converter.api.cache.MapCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;
import com.github.chaosfirebolt.converter.api.initialization.InitializationData;
import com.github.chaosfirebolt.converter.api.initialization.RomanIntegerArrayInitializationData;
import com.github.chaosfirebolt.converter.api.initialization.source.BasicNumeralsInputSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Map based roman integer cache.
 *
 * @deprecated Deprecated in favour of {@link UniDirectionalRomanIntegerCache}
 */
@Deprecated(since = "3.3.0", forRemoval = true)
public class MapRomanIntegerCache extends MapCache<String, RomanInteger> implements RomanIntegerCache {

  /**
   * Constructs an instance of Map based roman integer cache
   *
   * @param parserCache parsers cache
   */
  public MapRomanIntegerCache(ParserCache parserCache) {
    this(parserCache, new HashMap<>(), new RomanIntegerArrayInitializationData(new BasicNumeralsInputSource()));
  }

  /**
   * Constructs an instance of Map based roman integer cache
   *
   * @param parserCache        parsers cache
   * @param cache              map to cache roman integers in
   * @param initializationData source to initialize the cache with
   */
  public MapRomanIntegerCache(ParserCache parserCache, Map<String, RomanInteger> cache, InitializationData<Map<String, RomanInteger>> initializationData) {
    super(new DelegatingParser(parserCache), () -> new InvalidResultException("Returned value was null"), cache, initializationData);
  }
}
