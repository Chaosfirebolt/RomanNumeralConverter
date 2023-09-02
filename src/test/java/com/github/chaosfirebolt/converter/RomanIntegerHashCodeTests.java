package com.github.chaosfirebolt.converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanIntegerHashCodeTests {

    @Test
    public void hashCode_DiffObjects_SameArabic_DiffRoman_ShouldReturnSameHashCodeValues() {
        RomanInteger romanInteger = new RomanInteger("DCCCC", "900");
        RomanInteger another = new RomanInteger("CM", "900");
        assertEquals(romanInteger.hashCode(), another.hashCode(), "Romain integers representing same value returned different hash code");
    }

    @ParameterizedTest
    @ValueSource(strings = { "10", "11", "597", "2731", "3127" })
    public void hashCode_SameObjects_HashCodeShouldBeEqual(String value) {
        RomanInteger.enableCache();
        RomanInteger first = RomanInteger.parse(value);
        RomanInteger second = RomanInteger.parse(value);
        assertEquals(first.hashCode(), second.hashCode(), "Hash codes should have been the same");
        RomanInteger.disableCache();
    }
}
