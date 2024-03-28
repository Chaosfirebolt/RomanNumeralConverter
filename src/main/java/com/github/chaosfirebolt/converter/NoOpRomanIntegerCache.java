package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.OperationFailure;
import com.github.chaosfirebolt.converter.api.cache.DefaultCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;
import com.github.chaosfirebolt.converter.api.cache.storage.Computation;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;

/**
 * Non caching implementation for {@link RomanInteger} cache.
 */
class NoOpRomanIntegerCache extends DefaultCache<String, RomanInteger> implements RomanIntegerCache {

  /**
   * Constructs an instance of non caching roman integer cache
   *
   * @param parserCache parsers cache
   */
  NoOpRomanIntegerCache(ParserCache parserCache) {
    super(null, new DelegatingParser(parserCache), () -> new OperationFailure("Caching failure"));
  }

  @Override
  protected RomanInteger computeIfAbsent(Storage<String, RomanInteger> storage, String key, Computation<String, RomanInteger> computation) {
    return computation.compute(key);
  }

  @Override
  public void clear() {
  }

  @Override
  public void initialize() {
  }
}
