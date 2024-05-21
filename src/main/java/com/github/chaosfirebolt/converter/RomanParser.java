package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;

import java.util.Locale;

/**
 * Class used to parse strings in roman numeral format to {@link RomanInteger}.
 *
 * @see Parser
 */
public final class RomanParser extends BaseParser {

  /**
   * Constructs a new instance, providing the required {@link IntegerType}
   */
  public RomanParser() {
    super(IntegerType.ROMAN);
  }

  @Override
  public RomanInteger parse(String number) {
    number = integerType.validateFormat(number.trim().toUpperCase(Locale.ENGLISH));
    int arabic = 0;
    boolean add = true;
    for (int i = number.length() - 1; i >= 0; i--) {
      int current = arabic(number.charAt(i));
      if (i < number.length() - 1) {
        int previous = arabic(number.charAt(i + 1));
        if (current > previous) {
          add = true;
        } else if (current < previous) {
          add = false;
        }
      }
      arabic = add ? integerType.validateRange(arabic + current) : integerType.validateRange(arabic - current);
    }
    return new RomanInteger(number, integerType.validateRange(arabic));
  }

  private int arabic(char roman) {
    return pairMapping.getPair(roman)
            .orElseThrow(() -> new NumberFormatException("Unexpected roman numeral"))
            .arabic();
  }
}
