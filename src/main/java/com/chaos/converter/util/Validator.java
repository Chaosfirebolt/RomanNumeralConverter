package com.chaos.converter.util;

import com.chaos.converter.constants.IntegerType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ChaosFire on 01-Mar-18
 */
public class Validator {

    private static final Pattern ARABIC_PATTERN = Pattern.compile("^\\d+$");
    private static final Pattern ROMAN_PATTERN = Pattern.compile("^[IVXLCDM]+$");

    public static void validateInput(String number) {
        if (number == null || number.isEmpty()) {
            throw new IllegalArgumentException("Number can't be null or empty.");
        }
    }

    public static void validateArabic(String number, IntegerType integerType) {
        validate(number, ARABIC_PATTERN, integerType);
        int num = Integer.parseInt(number);
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Number must be in range 1 to 3999.");
        }
    }

    public static void validateRoman(String number, IntegerType integerType) {
        validate(number, ROMAN_PATTERN, integerType);
    }

    private static void validate(String number, Pattern pattern, IntegerType integerType) {
        Matcher matcher = pattern.matcher(number.trim());
        if (!matcher.find()) {
            throw new NumberFormatException(String.format("Invalid format of %s integer.", integerType.name()));
        }
    }
}