package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.constants.Patterns;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidatorTests {

    @Test
    public void range_ValidRange_ShouldReturnValidatedValue() {
        int arabic = 5;

        Integer expected = 5;
        Integer actual = Validator.range(arabic);
        assertEquals(expected, actual, "Returned value not as expected");
    }

    @Test
    public void range_InvalidRangeValueTooLow_ShouldThrowException() {
        assertIllegalArgumentExceptionThrownOnInvalidValue(0);
    }

    private static void assertIllegalArgumentExceptionThrownOnInvalidValue(int arabicValue) {
        assertThrows(IllegalArgumentException.class, () -> Validator.range(arabicValue), () -> "Expected IllegalArgumentException was not thrown for input - " + arabicValue);
    }

    @Test
    public void range_InvalidRangeValueTooHigh_ShouldThrowException() {
        assertIllegalArgumentExceptionThrownOnInvalidValue(4000);
    }

    @Test
    public void numberFormat_ValidRomanFormat_ShouldReturnValidatedValue_Test1() {
        String representation = "MCDLXVI";

        String actual = Validator.numberFormat(representation, Patterns.ROMAN_PATTERN);
        assertEquals(representation, actual);
    }

    @Test
    public void numberFormat_ValidRomanFormat_ShouldReturnValidatedValue_Test2() {
        String representation = "MLXVI";

        String actual = Validator.numberFormat(representation, Patterns.ROMAN_PATTERN);
        assertEquals(representation, actual);
    }

    @Test
    public void numberFormat_ValidRomanFormat_ShouldReturnValidatedValue_Test3() {
        String representation = "MCDLI";

        String actual = Validator.numberFormat(representation, Patterns.ROMAN_PATTERN);
        assertEquals(representation, actual);
    }

    @Test
    public void numberFormat_InvalidRomanFormat_ShouldThrowException_Test1() {
        assertNumberFormatExceptionThrownOnInvalidValue("MCDL XVI", Patterns.ROMAN_PATTERN);
    }

    private static void assertNumberFormatExceptionThrownOnInvalidValue(String input, Pattern pattern) {
        assertThrows(NumberFormatException.class, () -> Validator.numberFormat(input, pattern), () -> "Expected NumberFormatException was not thrown for input - " + input);
    }

    @Test
    public void numberFormat_InvalidRomanFormat_ShouldThrowException_Test2() {
        assertNumberFormatExceptionThrownOnInvalidValue("MCDLPXVI", Patterns.ROMAN_PATTERN);
    }

    @Test
    public void numberFormat_InvalidRomanFormat_ShouldThrowException_Test3() {
        assertNumberFormatExceptionThrownOnInvalidValue("MC9I", Patterns.ROMAN_PATTERN);
    }

    @Test
    public void numberFormat_InvalidRomanFormat_ShouldThrowException_Test4() {
        assertNumberFormatExceptionThrownOnInvalidValue("", Patterns.ROMAN_PATTERN);
    }

    @Test
    public void numberFormat_ValidArabicFormat_ShouldReturnValidatedValue() {
        String representation = "25";

        String actual = Validator.numberFormat(representation, Patterns.ARABIC_PATTERN);
        assertEquals(representation, actual);
    }

    @Test
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test1() {
        assertNumberFormatExceptionThrownOnInvalidValue("x", Patterns.ARABIC_PATTERN);
    }

    @Test
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test2() {
        assertNumberFormatExceptionThrownOnInvalidValue("", Patterns.ARABIC_PATTERN);
    }

    @Test
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test3() {
        assertNumberFormatExceptionThrownOnInvalidValue("25.0", Patterns.ARABIC_PATTERN);
    }

    @Test
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test4() {
        assertNumberFormatExceptionThrownOnInvalidValue("ML", Patterns.ARABIC_PATTERN);
    }

    @Test
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test5() {
        assertNumberFormatExceptionThrownOnInvalidValue("6,4", Patterns.ARABIC_PATTERN);
    }

    @Test
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test6() {
        assertNumberFormatExceptionThrownOnInvalidValue("41l", Patterns.ARABIC_PATTERN);
    }

    @Test
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test7() {
        assertNumberFormatExceptionThrownOnInvalidValue("4 8", Patterns.ARABIC_PATTERN);
    }
}