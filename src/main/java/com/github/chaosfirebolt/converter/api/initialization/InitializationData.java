package com.github.chaosfirebolt.converter.api.initialization;

/**
 * Represents a source used to fill the cache with data before usage.
 *
 * @param <T> type of the data used to fill the cache
 */
public sealed interface InitializationData<T> permits BaseInitializationData {

  /**
   * Gets the initialization source.
   *
   * @return the source
   */
  T getData();

  /**
   * Used to perform cleanup actions on the data returned from {@link #getData()}, if necessary.
   */
  void cleanup();
}
