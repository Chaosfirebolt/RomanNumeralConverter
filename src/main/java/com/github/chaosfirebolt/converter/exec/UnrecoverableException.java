package com.github.chaosfirebolt.converter.exec;

class UnrecoverableException extends RuntimeException {

  UnrecoverableException(Throwable cause) {
    super(cause.getMessage(), cause);
  }
}
