package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.initialization.ProvidedInitializationData;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProvidedDataSourceTests {

    @Test
    public void providedDataSourceShouldReturnAllValues() {
        ProvidedInitializationData source = new ProvidedInitializationData();
        Map<String, RomanInteger> actual = source.getData();
        Map<String, RomanInteger> expected = new HashMap<>();
        for (int number = 1; number <= 3999; number++) {
            RomanInteger romanInteger = RomanInteger.parse(Integer.toString(number));
            expected.put(romanInteger.getRoman(), romanInteger);
            expected.put(Integer.toString(romanInteger.getArabic()), romanInteger);
        }
        assertEquals(expected, actual, "Initial cache data not as expected");
    }

    @Test
    public void sourceShouldBeCleanedAfterCleanUp() {
        ProvidedInitializationData source = new ProvidedInitializationData();
        Map<String, RomanInteger> actual = source.getData();
        assertFalse(actual.isEmpty(), "Source should have had values");
        source.cleanup();
        assertTrue(actual.isEmpty(), "Source should have been empty after cleanup");
    }
}
