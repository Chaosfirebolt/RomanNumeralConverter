package com.github.chaosfirebolt.converter.api.cache.storage;

import java.util.function.Function;

/**
 * Represents a computation - function accepting one argument and producing a result.
 *
 * @param <I> type of the input
 * @param <O> type of the output
 * @since 3.3.0
 */
@FunctionalInterface
public interface Computation<I, O> {

  /**
   * Applies the computation to the argument.
   *
   * @param input the argument
   * @return computed result
   */
  O compute(I input);

  /**
   * Adapts this {@link Computation} as a {@link Function}.
   *
   * @return this computation adapted as a function
   */
  default Function<I, O> unwrap() {
    return unwrap(this);
  }

  /**
   * Adapts a {@link Function} as a {@link Computation}.
   *
   * @param function function to adapt
   * @return the function adapted as computation.
   * @param <T> type of the input
   * @param <R> type of the result
   */
  static <T, R> Computation<T, R> wrap(Function<T, R> function) {
    return new FunctionAdapter<>(function);
  }

  /**
   * Adapts a {@link Computation} as a {@link Function}.
   *
   * @param computation computation to adapt
   * @return the computation adapted as a function
   * @param <T> type of the input
   * @param <R> type of the result
   */
  static <T, R> Function<T, R> unwrap(Computation<T, R> computation) {
    return computation::compute;
  }
}
