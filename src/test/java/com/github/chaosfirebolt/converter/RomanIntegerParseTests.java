package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

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
    public void parseStringParam_TooLowInputArabic_ShouldThrowException() {
        assertExceptionThrown(IllegalArgumentException.class, () -> RomanInteger.parse("-19"));
    }

    private static void assertExceptionThrown(Class<? extends Exception> expectedException, Executable executable) {
        Exception exception = assertThrows(expectedException, executable, () -> String.format("%s was expected, but was not thrown", expectedException.getSimpleName()));
        String message = exception.getMessage();
        assertTrue(message != null && !message.isEmpty(), "Expected message missing");
    }

    @Test
    public void parseStringParam_TooHighInputArabic_ShouldThrowException() {
        assertExceptionThrown(IllegalArgumentException.class, () -> RomanInteger.parse("4263"));
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
    public void parseStringParam_InvalidRomanInput_ShouldReturnCorrect_Test1() {
        assertExceptionThrown(NumberFormatException.class, () -> RomanInteger.parse("X IV"));
    }

    @Test
    public void parseStringParam_InvalidRomanInput_ShouldReturnCorrect_Test2() {
        assertExceptionThrown(NumberFormatException.class, () -> RomanInteger.parse("Vh"));
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
    public void parseStringIntegerTypeParam_InvalidIntegerType_ShouldThrowException() {
        assertExceptionThrown(NumberFormatException.class, () -> RomanInteger.parse("16", IntegerType.ROMAN));
    }


    @Test
    public void parseStringIntegerTypeParam_InvalidIntegerType_Test2_ShouldThrowException() {
        assertExceptionThrown(NumberFormatException.class, () -> RomanInteger.parse("XXX", IntegerType.ARABIC));
    }

    @Test
    public void parseStringIntegerTypeParam_TooLowInputArabic_ShouldThrowException() {
        assertExceptionThrown(IllegalArgumentException.class, () -> RomanInteger.parse("0", IntegerType.ARABIC));
    }

    @Test
    public void parseStringIntegerTypeParam_TooHighInputArabic_ShouldThrowException() {
        assertExceptionThrown(IllegalArgumentException.class, () -> RomanInteger.parse("5000", IntegerType.ARABIC));
    }

    @Test
    public void parseStringIntegerTypeParam_ValidInputRoman_ShouldReturnCorrect() {
        String input = "MV";
        RomanInteger result = RomanInteger.parse(input, IntegerType.ROMAN);
        assertEquals(input, result.toString());
        int expectedArabic = 1005;
        assertEquals(expectedArabic, result.getArabic());
    }

    @Test
    public void parseStringIntegerTypeParam_InvalidInputRoman_ShouldThrowException() {
        assertExceptionThrown(NumberFormatException.class, () -> RomanInteger.parse("MV0", IntegerType.ROMAN));
    }

    @Test
    public void parseStringIntegerTypeParam_TooHighInputRoman_ShouldThrowException() {
        assertExceptionThrown(IllegalArgumentException.class, () -> RomanInteger.parse("MMMDDD", IntegerType.ROMAN));
    }
}