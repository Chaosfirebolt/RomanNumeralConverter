package com.github.chaosfirebolt.converter.api;

/**
 * Represent a cache for values
 * @param <K> type of the key, by which the value is saved
 * @param <V> type of the cached value
 */
public interface Cache<K, V> {

    /**
     * Searches for cached value by provided key.
     * @param key key to search the value by
     * @return cached value
     */
    V getValue(K key);
}
