package com.github.chaosfirebolt.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

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
        assertEquals(expectedRoman, result.getRoman(), "Roman representation not as expected");
    }

    @Test
    public void parseStringParam_ValidRomanInput_ShouldReturnCorrect() {
        String input = "XxIV";
        RomanInteger result = RomanInteger.parse(input);

        String expectedRoman = input.toUpperCase();
        assertEquals(expectedRoman, result.getRoman());
        int expectedArabic = 24;
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

    @ParameterizedTest()
    @ValueSource(strings = { "1", "11", "111" })
    public void cacheInitiallyDisabled_ShouldReturnDifferentInstances(String arabic) {
        assertInstancesNotSame(arabic);
    }

    private static void assertInstancesNotSame(String value) {
        RomanInteger first = RomanInteger.parse(value);
        RomanInteger second = RomanInteger.parse(value);
        assertNotSame(first, second, "Expected different instances, but were same");
    }

    @ParameterizedTest
    @ValueSource(strings = { "987", "1542", "3919" })
    public void cacheEnabled_ShouldReturnSameInstances(String arabic) {
        RomanInteger.enableCache();
        RomanInteger first = RomanInteger.parse(arabic);
        RomanInteger second = RomanInteger.parse(arabic);
        assertSame(first, second, "Expected same instances, but were different");
    }

    @ParameterizedTest
    @ValueSource(strings = { "VII", "IV", "XL" })
    public void cacheDisabled_ShouldReturnDifferentInstances(String roman) {
        RomanInteger.enableCache();
        RomanInteger.disableCache();
        assertInstancesNotSame(roman);
    }
}
