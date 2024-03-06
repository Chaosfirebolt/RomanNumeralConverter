package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.api.initialization.InitializationCapable;
import com.github.chaosfirebolt.converter.api.initialization.InitializationData;
import com.github.chaosfirebolt.converter.api.initialization.NoOpMapData;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Caches values in a {@link Map}.
 *
 * @param <K> type of the key, by which the value is saved
 * @param <V> type of the cached value
 */
public abstract class MapCache<K, V> extends BaseCache<K, V> implements InitializationCapable {

  /**
   * Map holding cached instances
   */
  private final Map<K, V> cache;
  private final InitializationData<Map<K, V>> initializationData;

  /**
   * @param computation       function to compute the value, if a cache is not found
   * @param exceptionSupplier supplier for an exception to be thrown, if a value can't be returned
   * @param cache             Map to store computed values in
   */
  protected MapCache(Function<K, V> computation, Supplier<? extends RuntimeException> exceptionSupplier, Map<K, V> cache) {
    this(computation, exceptionSupplier, cache, new NoOpMapData<>());
  }

  /**
   * @param computation        function to compute the value, if a cache is not found
   * @param exceptionSupplier  supplier for an exception to be thrown, if a value can't be returned
   * @param cache              Map to store computed values in
   * @param initializationData source of the initial data tobe stored in cache
   */
  protected MapCache(Function<K, V> computation, Supplier<? extends RuntimeException> exceptionSupplier, Map<K, V> cache, InitializationData<Map<K, V>> initializationData) {
    super(computation, exceptionSupplier);
    this.cache = cache;
    this.initializationData = initializationData;
  }

  @Override
  protected Optional<V> computeIfAbsent(K key, Function<K, V> computation) {
    V value = this.cacheIfAbsent(this.cache, key, computation);
    return Optional.ofNullable(value);
  }

  /**
   * Computes and caches the key-value pair, if there is no mapping found for this key.
   *
   * @param cache       cache to store value in
   * @param key         key to search the value by
   * @param computation function to compute the value, if a cache is not found
   * @return cached value, if present, otherwise computed and cached value
   */
  protected V cacheIfAbsent(Map<K, V> cache, K key, Function<K, V> computation) {
    return cache.computeIfAbsent(key, computation);
  }

  @Override
  public void clear() {
    this.cache.clear();
  }

  @Override
  public void initialize() {
    this.cache.putAll(this.initializationData.getData());
    this.initializationData.cleanup();
  }
}
