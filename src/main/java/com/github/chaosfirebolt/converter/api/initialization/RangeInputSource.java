package com.github.chaosfirebolt.converter.api.initialization;

import com.github.chaosfirebolt.converter.Parser;
import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.constants.IntegerType;

import java.util.stream.IntStream;

/**
 * Input source generating RomanInteger[] from a range of integers.
 * @since 3.2.0
 */
public class RangeInputSource implements InputSource<RomanInteger[]> {

    private final int startInclusive;
    private final int endInclusive;

    /**
     * Constructs a new instance using provided start and end for the range (inclusive).
     * Throws {@link IllegalArgumentException} if either parameter is outside allowed range
     * @param startInclusive arabic representation of the first roman integer in the range, inclusive
     * @param endInclusive arabic representation of the last roman integer in the array, inclusive
     * @throws IllegalArgumentException if provided integer is not in valid range.
     * @since 3.2.0
     */
    public RangeInputSource(int startInclusive, int endInclusive) {
        this.startInclusive = IntegerType.ARABIC.validateRange(startInclusive);
        this.endInclusive = IntegerType.ARABIC.validateRange(endInclusive);
    }

    @Override
    public RomanInteger[] getInputData() {
        Parser arabicParser = IntegerType.ARABIC.getParser();
        return IntStream.rangeClosed(this.startInclusive, this.endInclusive)
                .mapToObj(num -> arabicParser.parse(Integer.toString(num)))
                .toArray(RomanInteger[]::new);
    }
}
