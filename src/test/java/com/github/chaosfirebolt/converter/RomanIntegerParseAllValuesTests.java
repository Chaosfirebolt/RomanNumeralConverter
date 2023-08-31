package com.github.chaosfirebolt.converter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanIntegerParseAllValuesTests {

    private static final String ARABIC_ERROR_MESSAGE = "Arabic representation not as expected";
    private static final String ROMAN_ERROR_MESSAGE = "Roman representation not as expected";

    @ParameterizedTest
    @CsvFileSource(resources = "/all-values.csv", numLinesToSkip = 1)
    public void arabicToRoman(String arabic, String roman) {
        RomanInteger result = RomanInteger.parse(arabic);
        assertEquals(Integer.parseInt(arabic), result.getArabic(), ARABIC_ERROR_MESSAGE);
        assertEquals(roman, result.getRoman(), ROMAN_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/all-values.csv", numLinesToSkip = 1)
    public void romanToArabic(int arabic, String roman) {
        RomanInteger result = RomanInteger.parse(roman);
        assertEquals(arabic, result.getArabic(), ARABIC_ERROR_MESSAGE);
        assertEquals(roman, result.getRoman(), ROMAN_ERROR_MESSAGE);
    }
}
