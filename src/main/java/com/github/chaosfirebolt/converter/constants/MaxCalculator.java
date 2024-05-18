package com.github.chaosfirebolt.converter.constants;

//TODO move to PairMap, maybe cache result?
class MaxCalculator {

  static int calculateMax(int maxRegisteredValue) {
    int currentHigh = maxRegisteredValue;
    int max = currentHigh * 3;
    while (currentHigh > 1) {
      int previousOrder = currentHigh / 10;
      max += (currentHigh - previousOrder);
      currentHigh = previousOrder;
    }
    return max;
  }
}
