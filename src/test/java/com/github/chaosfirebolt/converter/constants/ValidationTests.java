package com.github.chaosfirebolt.converter.constants;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationTests {

    @ParameterizedTest
    @MethodSource("valuesInRange")
    public void range_ValidRange_ShouldReturnValidatedValue(IntegerType integerType, int value) {
        int actual = integerType.validateRange(value);
        assertEquals(value, actual, () -> "Returned value not as expected for integer type - " + integerType);
    }

    private static List<Arguments> buildRangeArguments(int... valuesToTest) {
        IntegerType[] types = IntegerType.values();
        List<Arguments> argumentsList = new ArrayList<>(types.length * valuesToTest.length);
        for (IntegerType integerType : types) {
            for (int value : valuesToTest) {
                argumentsList.add(Arguments.of(integerType, value));
            }
        }
        return argumentsList;
    }

    private static List<Arguments> valuesInRange() {
        return buildRangeArguments(5, 111, 3961, 857, 1737);
    }

    @ParameterizedTest
    @MethodSource("valuesOutsideOfRange")
    public void valueNotInRange_ShouldThrowException(IntegerType integerType, int arabicValue) {
        Supplier<String> errorMessageSupplier = () -> String.format("Expected IllegalArgumentException was not thrown by integer type %s for input - %d", integerType, arabicValue);
        Exception exc = assertThrows(IllegalArgumentException.class, () -> integerType.validateRange(arabicValue), errorMessageSupplier);
        assertExceptionMessage(exc.getMessage());
    }

    private static List<Arguments> valuesOutsideOfRange() {
        return buildRangeArguments(-3, 0, 4000);
    }

    private static void assertExceptionMessage(String message) {
        assertTrue(message != null && !message.isEmpty(), "Error message expected, but not found");
    }

    @ParameterizedTest
    @ValueSource(strings = { "MCDLXVI", "MLXVI", "MCDLI" })
    public void validRomanFormat_ShouldReturnValidatedValue(String value) {
        String actual = IntegerType.ROMAN.validateFormat(value);
        assertEquals(value, actual, "Returned value not as expected");
    }

    @ParameterizedTest
    @ValueSource(strings = { "25", "437", "2761" })
    public void numberFormat_ValidArabicFormat_ShouldReturnValidatedValue(String value) {
        String actual = IntegerType.ARABIC.validateFormat(value);
        assertEquals(value, actual, "Returned value not as expected");
    }

    @ParameterizedTest
    @MethodSource("invalidFormatData")
    public void invalidNumberFormat_ShouldThrowException(String input, IntegerType integerType) {
        Exception exc = assertThrows(NumberFormatException.class, () -> integerType.validateFormat(input), () -> "Expected NumberFormatException was not thrown for input - " + input);
        assertExceptionMessage(exc.getMessage());
    }

    private static Stream<Arguments> invalidFormatData() {
        return Stream.of(Arguments.of("MCDL XVI", IntegerType.ROMAN), Arguments.of("MCDLPXVI", IntegerType.ROMAN),
                        Arguments.of("MC9I", IntegerType.ROMAN), Arguments.of("", IntegerType.ROMAN),
                        Arguments.of("x", IntegerType.ARABIC), Arguments.of("", IntegerType.ARABIC),
                        Arguments.of("25.0", IntegerType.ARABIC), Arguments.of("ML", IntegerType.ARABIC),
                        Arguments.of("6,4", IntegerType.ARABIC), Arguments.of("41l", IntegerType.ARABIC),
                        Arguments.of("4 8", IntegerType.ARABIC));
    }
}
