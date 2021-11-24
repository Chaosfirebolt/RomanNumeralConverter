package com.github.chaosfirebolt.converter.util;

import java.util.regex.Pattern;

/**
 * Created by ChaosFire on 01-Mar-18
 *
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
     * Verifies that provided string matches provided pattern.
     *
     * @param representation string to verify.
     * @param pattern pattern that provided string must match.
     * @return provided string if it matches provided pattern.
     * @throws NumberFormatException if provided string does not match provided format.
     */
    public static String numberFormat(String representation, Pattern pattern) {
        if (!pattern.matcher(representation).find()) {
            throw new NumberFormatException("Number does not match required format for string: " + representation);
        }
        return representation;
    }

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