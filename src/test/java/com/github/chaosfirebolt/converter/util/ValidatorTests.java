package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.constants.Patterns;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidatorTests {

    @Test
    public void range_ValidRange_ShouldReturnValidatedValue() {
        Integer arabic = 5;

        Integer expected = 5;
        Integer actual = Validator.range(arabic);
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void range_InvalidRangeValueTooLow_ShouldThrowException() {
        Integer arabic = 0;
        Validator.range(arabic);
    }

    @Test(expected = IllegalArgumentException.class)
    public void range_InvalidRangeValueTooHigh_ShouldThrowException() {
        Integer arabic = 4000;
        Validator.range(arabic);
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

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidRomanFormat_ShouldThrowException_Test1() {
        String representation = "MCDL XVI";
        Validator.numberFormat(representation, Patterns.ROMAN_PATTERN);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidRomanFormat_ShouldThrowException_Test2() {
        String representation = "MCDLPXVI";
        Validator.numberFormat(representation, Patterns.ROMAN_PATTERN);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidRomanFormat_ShouldThrowException_Test3() {
        String representation = "MC9I";
        Validator.numberFormat(representation, Patterns.ROMAN_PATTERN);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidRomanFormat_ShouldThrowException_Test4() {
        String representation = "";
        Validator.numberFormat(representation, Patterns.ROMAN_PATTERN);
    }

    @Test
    public void numberFormat_ValidArabicFormat_ShouldReturnValidatedValue() {
        String representation = "25";

        String actual = Validator.numberFormat(representation, Patterns.ARABIC_PATTERN);
        assertEquals(representation, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test1() {
        String representation = "x";
        Validator.numberFormat(representation, Patterns.ARABIC_PATTERN);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test2() {
        String representation = "";
        Validator.numberFormat(representation, Patterns.ARABIC_PATTERN);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test3() {
        String representation = "25.0";
        Validator.numberFormat(representation, Patterns.ARABIC_PATTERN);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test4() {
        String representation = "ML";
        Validator.numberFormat(representation, Patterns.ARABIC_PATTERN);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test5() {
        String representation = "6,4";
        Validator.numberFormat(representation, Patterns.ARABIC_PATTERN);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test6() {
        String representation = "41l";
        Validator.numberFormat(representation, Patterns.ARABIC_PATTERN);
    }

    @Test(expected = NumberFormatException.class)
    public void numberFormat_InvalidArabicFormat_ShouldThrowException_Test7() {
        String representation = "4 8";
        Validator.numberFormat(representation, Patterns.ARABIC_PATTERN);
    }
}