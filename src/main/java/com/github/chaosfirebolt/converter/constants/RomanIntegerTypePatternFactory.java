package com.github.chaosfirebolt.converter.constants;

import com.github.chaosfirebolt.converter.util.Pair;
import com.github.chaosfirebolt.converter.util.PairMap;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;

class RomanIntegerTypePatternFactory implements Supplier<Pattern> {

  private static final String PREFIX = "^[";
  private static final String SUFFIX = "]+$";

  @Override
  public Pattern get() {
    List<Pair> romanSymbols = PairMap.getInstance().pairs();
    int requiredCapacity = PREFIX.length() + SUFFIX.length() + romanSymbols.size();
    StringBuilder sb = new StringBuilder(requiredCapacity);
    sb.append(PREFIX);
    for (Pair pair : romanSymbols) {
      sb.append(pair.roman());
    }
    sb.append(SUFFIX);
    return Pattern.compile(sb.toString());
  }
}
