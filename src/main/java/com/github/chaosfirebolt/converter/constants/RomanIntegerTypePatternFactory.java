package com.github.chaosfirebolt.converter.constants;

import com.github.chaosfirebolt.converter.util.PairMap;

import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Pattern;

class RomanIntegerTypePatternFactory implements Supplier<Pattern> {

  private static final String PREFIX = "^[";
  private static final String SUFFIX = "]+$";

  @Override
  public Pattern get() {
    Map<String, Integer> romanToArabic = PairMap.getInstance().getRomanToArabic();
    int requiredCapacity = PREFIX.length() + SUFFIX.length() + romanToArabic.size();
    StringBuilder sb = new StringBuilder(requiredCapacity);
    sb.append(PREFIX);
    for (String romanNumeral : romanToArabic.keySet()) {
      sb.append(romanNumeral);
    }
    sb.append(SUFFIX);
    return Pattern.compile(sb.toString());
  }
}
