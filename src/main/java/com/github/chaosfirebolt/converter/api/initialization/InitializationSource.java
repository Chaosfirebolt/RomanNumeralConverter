package com.github.chaosfirebolt.converter.api.initialization;

/**
 * Represents a source used to fill the cache with data before usage.
 * @param <T> type of the data used to fill the cache
 */
public sealed interface InitializationSource<T> permits BaseInitializationSource {

    /**
     * Gets the initialization source.
     * @return the source
     */
    T getSource();

    /**
     * Used to perform cleanup actions on the data returned from {@link #getSource()}, if necessary.
     */
    void cleanup();
}
