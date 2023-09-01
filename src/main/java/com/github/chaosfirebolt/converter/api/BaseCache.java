package com.github.chaosfirebolt.converter.api;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Base class for {@link Cache} implementations.
 * @param <K> type of the key, by which the value is saved
 * @param <V> type of the cached value
 */
public abstract class BaseCache<K, V> implements Cache<K, V> {

    private final Function<K, V> computation;
    private final Supplier<? extends RuntimeException> exceptionSupplier;

    /**
     * Constructs an instance with the specified computation and exception supplier
     * @param computation function to compute the value, if a cache is not found
     * @param exceptionSupplier supplier for an exception to be thrown, if a value can't be returned
     */
    protected BaseCache(Function<K, V> computation, Supplier<? extends RuntimeException> exceptionSupplier) {
        this.computation = computation;
        this.exceptionSupplier = exceptionSupplier;
    }

    /**
     *
     * @param key key to search the value by
     * @return the value
     * @throws RuntimeException if can't return a value
     */
    @Override
    public final V getValue(K key) {
        return computeIfAbsent(key, this.computation).orElseThrow(this.exceptionSupplier);
    }

    /**
     * Computes and returns an optional value
     * @param key key to search the value by
     * @param computation computation to invoke, if there is no cached value
     * @return an optional value
     */
    protected abstract Optional<V> computeIfAbsent(K key, Function<K, V> computation);
}
