package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.api.cache.storage.Computation;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;
import com.github.chaosfirebolt.converter.api.initialization.InitializationCapable;
import com.github.chaosfirebolt.converter.api.initialization.InitializationData;
import com.github.chaosfirebolt.converter.api.initialization.NoOpMapData;

import java.util.Map;
import java.util.function.Supplier;

/**
 * The default cache implementation.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class DefaultCache<K, V> implements Cache<K, V>, InitializationCapable {

  private final Storage<K, V> storage;
  private final Computation<K, V> computation;
  private final InitializationData<Map<K, V>> initializationData;
  private final Supplier<? extends RuntimeException> exceptionSupplier;

  /**
   * Defaults to no initialization data.
   *
   * @param storage           storage for cached values
   * @param computation       function to compute the value, if a cache is not found
   * @param exceptionSupplier supplier for an exception to be thrown, if a value can't be returned
   */
  public DefaultCache(Storage<K, V> storage, Computation<K, V> computation, Supplier<? extends RuntimeException> exceptionSupplier) {
    this(storage, computation, new NoOpMapData<>(), exceptionSupplier);
  }

  /**
   * @param storage            storage for cached values
   * @param computation        function to compute the value, if a cache is not found
   * @param initializationData source of the initial data tobe stored in cache
   * @param exceptionSupplier  supplier for an exception to be thrown, if a value can't be returned
   */
  public DefaultCache(Storage<K, V> storage, Computation<K, V> computation, InitializationData<Map<K, V>> initializationData, Supplier<? extends RuntimeException> exceptionSupplier) {
    this.storage = storage;
    this.computation = computation;
    this.initializationData = initializationData;
    this.exceptionSupplier = exceptionSupplier;
  }

  /**
   * @param key key to search the value by
   * @return the value
   * @throws RuntimeException     thrown if unable to return a value
   * @throws NullPointerException if key is null
   */
  @Override
  public final V getValue(K key) {
    try {
      return computeIfAbsent(storage, key, computation);
    } catch (NullPointerException | IllegalArgumentException exc) {
      throw exc;
    } catch (Exception cause) {
      RuntimeException exc = exceptionSupplier.get();
      exc.initCause(cause);
      throw exc;
    }
  }

  /**
   * Computes, if necessary and returns the value.
   *
   * @param storage     storage for cached values
   * @param key         key to search the value by
   * @param computation computation to invoke, if there is no cached value
   * @return the value
   */
  protected V computeIfAbsent(Storage<K, V> storage, K key, Computation<K, V> computation) {
    return storage.compute(key, computation);
  }

  @Override
  public void clear() {
    storage.clear();
  }

  @Override
  public void initialize() {
    for (Map.Entry<K, V> entry : initializationData.getData().entrySet()) {
      storage.store(entry.getKey(), entry.getValue());
    }
    initializationData.cleanup();
  }
}
