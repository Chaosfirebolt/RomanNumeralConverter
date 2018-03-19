package com.github.chaosfirebolt.converter.constants;

import java.util.regex.Pattern;

/**
 * Created by ChaosFire on 14.3.2018 г.
 *
 * Holds possible patterns, that a string must math in order to be parsed to a roman integer.
 */
public class Patterns {

    /**
     * Strings matching this pattern can be parsed to arabic format integers.
     */
    public static final Pattern ARABIC_PATTERN = Pattern.compile("^\\d+$");
    /**
     * Strings matching this pattern can be parsed to roman numeral format.
     */
    public static final Pattern ROMAN_PATTERN = Pattern.compile("^[IVXLCDM]+$");
}