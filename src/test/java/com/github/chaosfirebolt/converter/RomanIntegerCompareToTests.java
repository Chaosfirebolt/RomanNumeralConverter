package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.junit.RomanIntegerConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanIntegerCompareToTests {

    @ParameterizedTest
    @CsvFileSource(resources = "/compareTo.csv", numLinesToSkip = 1)
    public void test(@ConvertWith(RomanIntegerConverter.class) RomanInteger first, @ConvertWith(RomanIntegerConverter.class) RomanInteger second, int expectedResult) {
        int actualResult = first.compareTo(second);
        assertEquals(expectedResult, actualResult, "Comparison result not as expected");
    }
}
