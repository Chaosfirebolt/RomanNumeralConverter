package com.github.chaosfirebolt.converter.constants;

import com.github.chaosfirebolt.converter.util.PairMap;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

class RecalculateByPairCount implements BooleanSupplier {

  private static final int INITIAL_COUNT = -1;

  private final IntSupplier sizeSupplier;
  private int expectedSize;

  RecalculateByPairCount() {
    this(() -> PairMap.getInstance().count());
  }

  RecalculateByPairCount(IntSupplier sizeSupplier) {
    this.sizeSupplier = sizeSupplier;
    this.expectedSize = INITIAL_COUNT;
  }

  @Override
  public boolean getAsBoolean() {
    if (expectedSize == INITIAL_COUNT) {
      expectedSize = sizeSupplier.getAsInt();
      return true;
    }
    int actualSize = sizeSupplier.getAsInt();
    if (actualSize != expectedSize) {
      expectedSize = actualSize;
      return true;
    }
    return false;
  }
}
