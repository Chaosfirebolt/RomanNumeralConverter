package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.util.Pair;
import com.github.chaosfirebolt.converter.util.PairMap;

import java.util.function.Supplier;

/**
 * Class used to parse strings in arabic numeral format to {@link RomanInteger}.
 *
 * @see Parser
 */
public final class ArabicParser extends BaseParser {

  private static final Supplier<NumberFormatException> ERR = () -> new NumberFormatException("Unexpected arabic numeral");

  /**
   * Constructs a new instance, providing the required {@link IntegerType}
   */
  public ArabicParser() {
    super(IntegerType.ARABIC);
  }

  @Override
  public RomanInteger parse(String number) {
    number = integerType.validateFormat(number.trim());
    int arabicValue = integerType.validateRange(Integer.parseInt(number));
    StringBuilder roman = new StringBuilder();
    PairMap pairMap = PairMap.getInstance();
    for (int i = 0; i < number.length(); i++) {
      int digit = Integer.parseInt(Character.toString(number.charAt(i)));
      int power = number.length() - 1 - i;
      int exp = (int) Math.pow(10, power);

      int key = digit * exp;
      if (digit == 4 || digit == 9) {
        roman.append(roman(pairMap, exp)).append(pairMap.getHigherPair(key).orElseThrow(ERR).roman());
      } else {
        while (key > 0) {
          Pair floorPair = pairMap.getFloorPair(key).orElseThrow(ERR);
          int repeat = key / floorPair.arabic();
          for (int j = 0; j < repeat; j++) {
            roman.append(floorPair.roman());
            key -= floorPair.arabic();
          }
        }
      }
    }
    return new RomanInteger(roman.toString(), arabicValue);
  }

  private static String roman(PairMap pairMap, int arabic) {
    return pairMap.getPair(arabic)
            .orElseThrow(ERR)
            .roman();
  }
}
