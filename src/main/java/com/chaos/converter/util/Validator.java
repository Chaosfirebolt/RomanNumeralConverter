package com.chaos.converter.util;

import java.util.regex.Pattern;

/**
 * Created by ChaosFire on 01-Mar-18
 */
public class Validator {

    private static final Integer MIN = 1;
    private static final Integer MAX = 3999;

    public static String numberFormat(String representation, Pattern pattern) {
        if (!pattern.matcher(representation).find()) {
            throw new NumberFormatException("Number does not match required format for string: " + representation);
        }
        return representation;
    }

    public static Integer range(Integer arabic) {
        if (arabic < MIN || arabic > MAX) {
            throw new IllegalArgumentException(String.format("Valid range for roman integers is from %d to %d inclusive.", MIN, MAX));
        }
        return arabic;
    }
}