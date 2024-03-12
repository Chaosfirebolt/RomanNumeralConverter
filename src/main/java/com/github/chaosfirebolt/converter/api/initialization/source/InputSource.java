package com.github.chaosfirebolt.converter.api.initialization.source;

/**
 * Represents a data source.
 *
 * @param <I> type of the returned data
 */
@FunctionalInterface
public interface InputSource<I> {

  /**
   * Gets the data from this source
   *
   * @return data
   */
  I getInputData();
}
