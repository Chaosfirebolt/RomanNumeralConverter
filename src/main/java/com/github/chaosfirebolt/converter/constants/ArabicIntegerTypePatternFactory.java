package com.github.chaosfirebolt.converter.constants;

import java.util.function.Supplier;
import java.util.regex.Pattern;

class ArabicIntegerTypePatternFactory implements Supplier<Pattern> {

  private final Pattern arabicPattern = Pattern.compile("^\\d+$");

  @Override
  public Pattern get() {
    return arabicPattern;
  }
}
