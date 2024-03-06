package com.github.chaosfirebolt.converter.api.cache.storage;

import java.util.Optional;

/**
 * Storage for cached values.
 *
 * @param <K> type of the key, by which the value is saved
 * @param <V> type of the cached value
 * @since 3.3.0
 */
public interface Storage<K, V> {

  /**
   * Stores the key-value pair. If the store previously contained a value associated with this key,
   * the old value is replaced by the new value.
   *
   * @param key to associate the value with
   * @param value the value
   * @throws NullPointerException if either key or value is null
   */
  void store(K key, V value);

  /**
   * Retrieves the value associated with the specified key from the storage.
   * If the storage does not contain value associated with the key, returns empty optional.
   *
   * @param key key to search the value by
   * @return optional with a value, if there is value associated with the key, otherwise empty optional
   * @throws NullPointerException if key is null
   */
  Optional<V> retrieve(K key);

  /**
   * Retrieves the value associated with the key, if present, otherwise computes a value, using provided computation, stores and returns computed value.
   *
   * @param key key to search the value by
   * @param computation function accepting the key  and computing a value, if value is not present
   * @return stored value if present, otherwise computed value
   * @throws NullPointerException if either key or computed value is null
   */
  default V compute(K key, Computation<K, V> computation) {
    Optional<V> storedValue = retrieve(key);
    if (storedValue.isPresent()) {
      return storedValue.get();
    }
    V computedValue = computation.compute(key);
    store(key, computedValue);
    return computedValue;
  }

  /**
   * Removes the key and its associated value, if present.
   *
   * @param key key to search the value by
   * @throws NullPointerException if key is null
   */
  void remove(K key);

  /**
   * Removes all associations from the storage.
   */
  void clear();
}
