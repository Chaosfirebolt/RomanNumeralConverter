package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.testUtil.Constants;
import com.github.chaosfirebolt.converter.testUtil.FieldAccessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RomanIntegerTests {

    @Test
    public void equals_AnotherObject_ShouldReturnFalse() {
        Object ten = 10;
        assertNotEquals(RomanInteger.TEN, ten, "Equals should have returned false");
    }

    @Test
    public void equals_SameArabic_SameRoman_ShouldReturnTrue() {
        RomanInteger romanInteger = new RomanInteger("IX", "9");
        RomanInteger another = new RomanInteger("IX", "9");
        assertEquals(romanInteger, another, "Roman integers were not equal");
    }

    @Test
    public void equals_SameArabic_DiffRoman_ShouldReturnTrue() {
        RomanInteger romanInteger = new RomanInteger("IX", "9");
        RomanInteger another = new RomanInteger("VIIII", "9");
        assertEquals(romanInteger, another, "Roman integers were not equal");
    }

    @Test
    public void equals_DiffArabic_DiffRoman_ShouldReturnFalse() {
        RomanInteger romanInteger = new RomanInteger("IX", "9");
        RomanInteger another = new RomanInteger("XXX", "30");
        assertNotEquals(romanInteger, another, "Roman integers were equals, despite representing different values");
    }

    @Test
    public void hashCode_DiffObjects_SameArabic_DiffRoman_ShouldReturnSameHashCodeValues() {
        RomanInteger romanInteger = new RomanInteger("DCCCC", "900");
        RomanInteger another = new RomanInteger("CM", "900");
        assertEquals(romanInteger.hashCode(), another.hashCode(), "Romain integers representing same value returned different hash code");
    }

    @Test
    public void CloneConstructor_ShouldReturnDifferentObject() {
        RomanInteger source = RomanInteger.FIFTY;
        RomanInteger copy = source.clone();
        assertNotSame(source, copy, "Clone returned same instance");
    }

    @Test
    public void CloneConstructor_LooseMode_ValuesShouldBeTheSame() {
        RomanInteger source = RomanInteger.FIFTY;
        RomanInteger copy = source.clone();
        assertEqualFieldValues(source, copy);
    }

    private static void assertEqualFieldValues(RomanInteger source, RomanInteger copy) {
        for (String fieldName : Constants.FIELD_NAMES) {
            Object expected = FieldAccessor.getValue(source, fieldName);
            Object actual = FieldAccessor.getValue(copy, fieldName);
            assertEquals(expected, actual, () -> String.format("Value for field '%s' not as expected - ", fieldName));
        }
    }
}