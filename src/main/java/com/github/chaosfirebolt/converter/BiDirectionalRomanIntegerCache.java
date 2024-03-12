package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.storage.Computation;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;
import com.github.chaosfirebolt.converter.api.initialization.InitializationData;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Cache saving computed roman integers in both directions - roman value to roman integer and arabic value to roman integer.
 *
 * @since 3.3.0
 */
public final class BiDirectionalRomanIntegerCache extends DefaultRomanIntegerCache {

  /**
   * @param parserCache cache for parser instances
   */
  public BiDirectionalRomanIntegerCache(ParserCache parserCache) {
    super(parserCache);
  }

  /**
   * @param parserCache        cache for parser instances
   * @param storage            storage for key to computed value
   * @param initializationData data to pre initialize the cache with
   */
  public BiDirectionalRomanIntegerCache(ParserCache parserCache, Storage<String, RomanInteger> storage, InitializationData<Map<String, RomanInteger>> initializationData) {
    super(parserCache, storage, initializationData);
  }

  @Override
  protected RomanInteger computeIfAbsent(Storage<String, RomanInteger> storage, String key, Computation<String, RomanInteger> computation) {
    Optional<RomanInteger> cachedValue = storage.retrieve(key);
    return cachedValue.orElseGet(new ComputeAndStore(storage, key, computation));
  }

  private record ComputeAndStore(Storage<String, RomanInteger> storage, String key, Computation<String, RomanInteger> computation) implements Supplier<RomanInteger> {

    @Override
    public RomanInteger get() {
      RomanInteger computedValue = computation.compute(key);
      storage.store(computedValue.getRoman(), computedValue);
      storage.store(Integer.toString(computedValue.getArabic()), computedValue);
      return computedValue;
    }
  }
}
