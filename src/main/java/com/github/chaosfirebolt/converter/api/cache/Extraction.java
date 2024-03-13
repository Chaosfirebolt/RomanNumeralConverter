package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.api.cache.storage.Computation;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;

/**
 * Represents the process of extracting a value associated with a key from a storage.
 *
 * @param <K> key type
 * @param <V> value type
 */
@FunctionalInterface
public interface Extraction<K, V> {

  /**
   * Extracts a value.
   *
   * @param storage     storage to extract value from
   * @param key         key the value should be associated with
   * @param computation computation to calculate the value if one is missing
   * @return the value
   */
  V extract(Storage<K, V> storage, K key, Computation<K, V> computation);
}
