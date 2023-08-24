package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.constants.Patterns;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTests {

    @ParameterizedTest
    @ValueSource(ints = { 5, 111, 3961, 857, 1737 })
    public void range_ValidRange_ShouldReturnValidatedValue(int value) {
        int actual = Validator.range(value);
        assertEquals(value, actual, "Returned value not as expected");
    }

    @ParameterizedTest
    @ValueSource(ints = { -3, 0, 4000 })
    public void valueNotInRange_ShouldThrowException(int arabicValue) {
        Exception exc = assertThrows(IllegalArgumentException.class, () -> Validator.range(arabicValue), () -> "Expected IllegalArgumentException was not thrown for input - " + arabicValue);
        assertExceptionMessage(exc.getMessage());
    }

    private static void assertExceptionMessage(String message) {
        assertTrue(message != null && !message.isEmpty(), "Error message expected, but not found");
    }

    @ParameterizedTest
    @ValueSource(strings = { "MCDLXVI", "MLXVI", "MCDLI" })
    public void validRomanFormat_ShouldReturnValidatedValue(String value) {
        String actual = Validator.numberFormat(value, Patterns.ROMAN_PATTERN);
        assertEquals(value, actual, "Returned value not as expected");
    }

    @ParameterizedTest
    @ValueSource(strings = { "25", "437", "2761" })
    public void numberFormat_ValidArabicFormat_ShouldReturnValidatedValue(String value) {
        String actual = Validator.numberFormat(value, Patterns.ARABIC_PATTERN);
        assertEquals(value, actual, "Returned value not as expected");
    }

    @ParameterizedTest
    @MethodSource("invalidFormatData")
    public void invalidNumberFormat_ShouldThrowException(String input, Pattern pattern) {
        Exception exc = assertThrows(NumberFormatException.class, () -> Validator.numberFormat(input, pattern), () -> "Expected NumberFormatException was not thrown for input - " + input);
        assertExceptionMessage(exc.getMessage());
    }

    private static Stream<Arguments> invalidFormatData() {
        return Stream.of(Arguments.of("MCDL XVI", Patterns.ROMAN_PATTERN), Arguments.of("MCDLPXVI", Patterns.ROMAN_PATTERN),
                        Arguments.of("MC9I", Patterns.ROMAN_PATTERN), Arguments.of("", Patterns.ROMAN_PATTERN),
                        Arguments.of("x", Patterns.ARABIC_PATTERN), Arguments.of("", Patterns.ARABIC_PATTERN),
                        Arguments.of("25.0", Patterns.ARABIC_PATTERN), Arguments.of("ML", Patterns.ARABIC_PATTERN),
                        Arguments.of("6,4", Patterns.ARABIC_PATTERN), Arguments.of("41l", Patterns.ARABIC_PATTERN),
                        Arguments.of("4 8", Patterns.ARABIC_PATTERN));
    }
}