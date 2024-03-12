package com.github.chaosfirebolt.converter.api;

/**
 * Thrown to indicate that an operation could not be completed.
 */
public class OperationFailure extends RuntimeException {

  /**
   * Construct a new exception with the specified message.
   *
   * @param message details message
   */
  public OperationFailure(String message) {
    super(message);
  }
}
