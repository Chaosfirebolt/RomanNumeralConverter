package com.github.chaosfirebolt.converter.util;

import java.util.regex.Pattern;

/**
 * Utility class for validation purposes.
 */
public class Validator {

    /**
     * Minimal possible value of roman numeral.
     */
    private static final int MIN = 1;
    /**
     * Maximum possible value of roman numeral.
     */
    private static final int MAX = 3999;

    /**
     * Verifies that provided integer is in valid range for roman numerals.
     *
     * @param arabic integer to check.
     * @return provided integer, if it is in valid range.
     * @throws IllegalArgumentException if provided integer is not in valid range.
     */
    public static int range(int arabic) {
        if (arabic < MIN || arabic > MAX) {
            throw new IllegalArgumentException(String.format("Valid range for roman integers is from %d to %d inclusive.", MIN, MAX));
        }
        return arabic;
    }
}