package com.github.chaosfirebolt.converter.api.initialization.transformation;

import com.github.chaosfirebolt.converter.RomanInteger;

import java.util.HashMap;
import java.util.Map;

/**
 * Transforms array of {@link RomanInteger} to a bidirectional {@link Map}.
 */
public class RomanIntegerArrayToMapTransformation implements Transformation<RomanInteger[], Map<String, RomanInteger>> {

  @Override
  public Map<String, RomanInteger> transform(RomanInteger[] romanIntegers) {
    Map<String, RomanInteger> result = new HashMap<>(romanIntegers.length * 2);
    for (RomanInteger romanInteger : romanIntegers) {
      result.put(romanInteger.getRoman(), romanInteger);
      result.put(Integer.toString(romanInteger.getArabic()), romanInteger);
    }
    return result;
  }
}
