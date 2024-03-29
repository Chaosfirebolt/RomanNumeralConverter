package com.github.chaosfirebolt.converter.api;

/**
 * Thrown to indicate that either the cached result, or the computation result was invalid - mostly {@code null}.
 */
@Deprecated(since = "3.3.0", forRemoval = true)
public class InvalidResultException extends RuntimeException {

  /**
   * Construct a new exception with the specified message.
   *
   * @param message details message
   */
  public InvalidResultException(String message) {
    super(message);
  }
}
