package com.github.chaosfirebolt.converter.api.cache.storage;

import java.util.function.Function;

class FunctionAdapter<I, O> implements Computation<I, O> {

  private final Function<I, O> function;

  FunctionAdapter(Function<I, O> function) {
    this.function = function;
  }

  @Override
  public O compute(I input) {
    return function.apply(input);
  }

  @Override
  public Function<I, O> unwrap() {
    return function;
  }
}
