package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RomanIntegerParseTests {

    @Test
    public void parseStringParam_ValidInputArabic_ShouldReturnCorrect() {
        int input = 19;
        RomanInteger result = RomanInteger.parse(Integer.toString(input));
        assertEquals(input, result.getArabic(), "Arabic representation not as expected");
        String expectedRoman = "XIX";
        assertEquals(expectedRoman, result.toString(), "Roman representation not as expected");
    }

    @Test
    public void parseStringParam_ValidRomanInput_ShouldReturnCorrect() {
        String input = "XxIV";
        RomanInteger result = RomanInteger.parse(input);

        String expectedRoman = input.toUpperCase();
        assertEquals(expectedRoman, result.toString());
        int expectedArabic = 24;
        assertEquals(expectedArabic, result.getArabic());
    }

    @Test
    public void parseStringIntegerTypeParam_ValidInput_ShouldReturnCorrect() {
        int input = 16;
        RomanInteger result = RomanInteger.parse(Integer.toString(input), IntegerType.ARABIC);
        String expectedRoman = "XVI";
        assertEquals(expectedRoman, result.toString());
        assertEquals(input, result.getArabic());
    }

    @Test
    public void parseStringIntegerTypeParam_ValidInputRoman_ShouldReturnCorrect() {
        String input = "MV";
        RomanInteger result = RomanInteger.parse(input, IntegerType.ROMAN);
        assertEquals(input, result.toString());
        int expectedArabic = 1005;
        assertEquals(expectedArabic, result.getArabic());
    }

    @ParameterizedTest
    @MethodSource("invalidData")
    public void invalidInput_ShouldThrowException(Class<? extends Exception> expectedException, String input) {
        assertException(expectedException, () -> RomanInteger.parse(input), () -> String.format("%s expected, but not thrown for input - %s", expectedException.getSimpleName(), input));
    }

    private static Stream<Arguments> invalidData() {
        return Stream.of(Arguments.of(IllegalArgumentException.class, "-19"), Arguments.of(IllegalArgumentException.class, "4263"),
                        Arguments.of(NumberFormatException.class, "X IV"), Arguments.of(NumberFormatException.class, "Vh"));
    }

    private static void assertException(Class<? extends Exception> expectedException, Executable executable, Supplier<String> errorMessageSupplier) {
        Exception exception = assertThrows(expectedException, executable, errorMessageSupplier);
        String message = exception.getMessage();
        assertTrue(message != null && !message.isEmpty(), "Message expected, but not found");
    }

    @ParameterizedTest
    @MethodSource("invalidDataWithIntegerType")
    public void invalidInputForIntegerType_ShouldThrowException(Class<? extends Exception> expectedException, String input, IntegerType integerType) {
        assertException(expectedException, () -> RomanInteger.parse(input, integerType), () -> String.format("%s expected, but was not thrown for input - %s and type - %s", expectedException.getSimpleName(), input, integerType));
    }

    private static Stream<Arguments> invalidDataWithIntegerType() {
        return Stream.of(Arguments.of(NumberFormatException.class, "16", IntegerType.ROMAN),
                        Arguments.of(NumberFormatException.class, "XXX", IntegerType.ARABIC),
                        Arguments.of(IllegalArgumentException.class, "0", IntegerType.ARABIC), Arguments.of(IllegalArgumentException.class, "5000", IntegerType.ARABIC),
                        Arguments.of(NumberFormatException.class, "MV0", IntegerType.ROMAN), Arguments.of(IllegalArgumentException.class, "MMMDDD", IntegerType.ROMAN));
    }
}