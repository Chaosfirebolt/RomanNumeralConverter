package com.github.chaosfirebolt.converter.constants;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

class ResultCachingSupplier<T> implements Supplier<T> {

  private final Supplier<T> delegate;
  private final BooleanSupplier recalculateCondition;
  private T cachedValue;

  ResultCachingSupplier(Supplier<T> delegate, BooleanSupplier recalculateCondition) {
    this.delegate = delegate;
    this.recalculateCondition = recalculateCondition;
  }

  @Override
  public T get() {
    if (cachedValue == null || recalculateCondition.getAsBoolean()) {
      cachedValue = delegate.get();
    }
    return cachedValue;
  }
}
