package com.github.chaosfirebolt.converter.api.initialization;

import com.github.chaosfirebolt.converter.api.initialization.source.RangeInputSource;

/**
 * Initialization source implementation, using integer range.
 * @since 3.2.0
 */
public class RangeInitializationData extends RomanIntegerArrayInitializationData {

    /**
     * Constructs a new instance using provided start and end for the range (inclusive).
     * Throws {@link IllegalArgumentException} if either parameter is outside the allowed range
     * @param startInclusive arabic representation of the first roman integer in the range, inclusive
     * @param endInclusive arabic representation of the last roman integer in the array, inclusive
     * @throws IllegalArgumentException if provided integer is not in valid range.
     * @since 3.2.0
     */
    public RangeInitializationData(int startInclusive, int endInclusive) {
        super(new RangeInputSource(startInclusive, endInclusive));
    }
}
