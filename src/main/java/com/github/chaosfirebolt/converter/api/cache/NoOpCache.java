package com.github.chaosfirebolt.converter.api.cache;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Does not cache anything, computes value every time.
 *
 * @param <K> type of the key, by which the value is saved
 * @param <V> type of the cached value
 */
public abstract class NoOpCache<K, V> extends BaseCache<K, V> {

  /**
   * @param computation       function to compute the value, if a cache is not found
   * @param exceptionSupplier supplier for an exception to be thrown, if a value can't be returned
   */
  protected NoOpCache(Function<K, V> computation, Supplier<? extends RuntimeException> exceptionSupplier) {
    super(computation, exceptionSupplier);
  }

  @Override
  protected Optional<V> computeIfAbsent(K key, Function<K, V> computation) {
    return Optional.ofNullable(computation.apply(key));
  }

  @Override
  public void clear() {
  }
}
