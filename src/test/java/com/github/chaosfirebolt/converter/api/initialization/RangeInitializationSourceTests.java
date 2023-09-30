package com.github.chaosfirebolt.converter.api.initialization;

import com.github.chaosfirebolt.converter.RomanInteger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

public class RangeInitializationSourceTests {

    @ParameterizedTest
    @CsvSource({ "-1, 100", "0, 11", "-5, 233" })
    public void startOutsideOfRange_ShouldThrowIllegalArgumentException(int start, int end) {
        assertExceptionThrownForInvalidInput(start, end);
    }

    private static void assertExceptionThrownForInvalidInput(int start, int end) {
        Supplier<String> errorMessageSupplier = () -> String.format("Exception should have been thrown for range %d - %d", start, end);
        IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> new RangeInitializationSource(start, end), errorMessageSupplier);
        assertTrue(exc.getMessage() != null && !exc.getMessage().isEmpty(), "Exception should have had a message");
    }

    @ParameterizedTest
    @CsvSource({ "1, 4000", "526, 5698", "3999, 25987" })
    public void endOutsideOfRange_ShouldThrowIllegalArgumentException(int start, int end) {
        assertExceptionThrownForInvalidInput(start, end);
    }

    @ParameterizedTest
    @CsvSource({ "35, 11", "3999, 3998", "749, 123" })
    public void startIsLargerThanEnd_ShouldThrowIllegalArgumentException(int start, int end) {
        assertExceptionThrownForInvalidInput(start, end);
    }

    @ParameterizedTest
    @CsvSource({ "2, 2", "11, 250", "9, 3947"})
    public void validaRange_ShouldReturnCorrectResult(int start, int end) {
        RangeInitializationSource source = new RangeInitializationSource(start, end);
        Map<String, RomanInteger> actualMap = source.getSource();
        int expectedSize = ((end - start) + 1) * 2;
        assertEquals(expectedSize, actualMap.size(), "Size not as expected");

        Map<String, RomanInteger> expectedMap = new HashMap<>();
        for (int i = start; i <= end; i++) {
            RomanInteger romanInteger = RomanInteger.parse(Integer.toString(i));
            expectedMap.put(romanInteger.getRoman(), romanInteger);
            expectedMap.put(Integer.toString(romanInteger.getArabic()), romanInteger);
        }
        assertEquals(expectedMap, actualMap, "Result not as expected");
    }
}
