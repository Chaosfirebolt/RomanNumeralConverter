package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.storage.Computation;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

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
  protected RomanInteger computeIfAbsent(Storage<String, RomanInteger> storage, String key, Computation<String, RomanInteger> computation) {
    Optional<RomanInteger> cachedValue = storage.retrieve(key);
    return cachedValue.orElseGet(new ComputeAndStore(storage, key, computation));
  }


//  @Override
//  protected RomanInteger cacheIfAbsent(Map<String, RomanInteger> cache, String key, Function<String, RomanInteger> computation) {
//    RomanInteger cached = cache.get(key);
//    if (cached != null) {
//      return cached;
//    }
//    RomanInteger computed = computation.apply(key);
//    cache.putIfAbsent(computed.getRoman(), computed);
//    cache.putIfAbsent(Integer.toString(computed.getArabic()), computed);
//    return computed;
//  }

  private record ComputeAndStore(Storage<String, RomanInteger> storage, String key, Computation<String, RomanInteger> computation) implements Supplier<RomanInteger> {

    @Override
    public RomanInteger get() {
      RomanInteger computed = computation.compute(key);
      storage.store(computed.getRoman(), computed);
      storage.store(Integer.toString(computed.getArabic()), computed);
      return computed;
    }
  }
}
