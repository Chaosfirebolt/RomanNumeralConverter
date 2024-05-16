package com.github.chaosfirebolt.converter.constants;

import java.util.function.Supplier;

class SynchronizedSupplier<T> implements Supplier<T> {

  private final Supplier<T> delegate;

  SynchronizedSupplier(Supplier<T> delegate) {
    this.delegate = delegate;
  }

  @Override
  public synchronized T get() {
    return delegate.get();
  }
}
