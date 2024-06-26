package com.github.chaosfirebolt.converter.api.initialization.source;

import com.github.chaosfirebolt.converter.Parser;
import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.constants.IntegerType;

import java.util.stream.IntStream;

/**
 * Input source generating RomanInteger[] from a range of integers.
 *
 * @since 3.2.0
 */
public class RangeInputSource implements InputSource<RomanInteger[]> {

  private final int startInclusive;
  private final int endInclusive;

  /**
   * Constructs a new instance using provided start and end for the range (inclusive).
   * Throws {@link IllegalArgumentException} if either parameter is outside allowed range
   *
   * @param startInclusive arabic representation of the first roman integer in the range, inclusive
   * @param endInclusive   arabic representation of the last roman integer in the array, inclusive
   * @throws IllegalArgumentException if provided integer is not in valid range.
   * @since 3.2.0
   */
  public RangeInputSource(int startInclusive, int endInclusive) {
    validateStartIsBeforeEnd(startInclusive, endInclusive);
    this.startInclusive = IntegerType.ARABIC.validateRange(startInclusive);
    this.endInclusive = IntegerType.ARABIC.validateRange(endInclusive);
  }

  private static void validateStartIsBeforeEnd(int start, int end) {
    if (start > end) {
      throw new IllegalArgumentException("Start must be equal or less than end");
    }
  }

  @Override
  public RomanInteger[] getInputData() {
    Parser arabicParser = IntegerType.ARABIC.getParser();
    return IntStream.rangeClosed(startInclusive, endInclusive)
            .mapToObj(num -> fromArabic(num, arabicParser))
            .toArray(RomanInteger[]::new);
  }

  private static RomanInteger fromArabic(int arabic, Parser arabicParser) {
    return switch (arabic) {
      case 1 -> RomanInteger.ONE;
      case 5 -> RomanInteger.FIVE;
      case 10 -> RomanInteger.TEN;
      case 50 -> RomanInteger.FIFTY;
      case 100 -> RomanInteger.HUNDRED;
      case 500 -> RomanInteger.FIVE_HUNDRED;
      case 1000 -> RomanInteger.THOUSAND;
      default -> arabicParser.parse(Integer.toString(arabic));
    };
  }
}
