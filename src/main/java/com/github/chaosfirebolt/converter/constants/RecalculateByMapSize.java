package com.github.chaosfirebolt.converter.constants;

import com.github.chaosfirebolt.converter.util.PairMap;

import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

class RecalculateByMapSize implements BooleanSupplier {

  private static final int INITIAL_SIZE = -1;

  private final IntSupplier sizeSupplier;
  private int expectedSize;

  RecalculateByMapSize() {
    this(() -> PairMap.getInstance().getRomanToArabic().size());
  }

  RecalculateByMapSize(IntSupplier sizeSupplier) {
    this.sizeSupplier = sizeSupplier;
    this.expectedSize = INITIAL_SIZE;
  }

  @Override
  public boolean getAsBoolean() {
    if (expectedSize == INITIAL_SIZE) {
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
