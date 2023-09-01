package com.github.chaosfirebolt.converter.api.cache;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Caches values in a {@link Map}.
 * @param <K> type of the key, by which the value is saved
 * @param <V> type of the cached value
 */
public abstract class MapCache<K, V> extends BaseCache<K, V> {

    /**
     * Map holding cached instances
     */
    private final Map<K, V> cache;

    /**
     * @param computation function to compute the value, if a cache is not found
     * @param exceptionSupplier supplier for an exception to be thrown, if a value can't be returned
     * @param cache Map to store computed values in
     */
    protected MapCache(Function<K, V> computation, Supplier<? extends RuntimeException> exceptionSupplier, Map<K, V> cache) {
        super(computation, exceptionSupplier);
        this.cache = cache;
    }

    @Override
    protected Optional<V> computeIfAbsent(K key, Function<K, V> computation) {
        V value = this.cache.computeIfAbsent(key, computation);
        return Optional.ofNullable(value);
    }
}
