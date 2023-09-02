package com.github.chaosfirebolt.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RomanIntegerEqualsTests {

    @Test
    public void equals_AnotherObject_ShouldReturnFalse() {
        Object ten = 10;
        assertNotEquals(RomanInteger.TEN, ten, "Equals should have returned false");
    }

    @Test
    public void equals_SameObject_ShouldReturnTrue() {
        RomanInteger.enableCache();
        assertEquals(RomanInteger.parse("11"), RomanInteger.parse("11"), "Same objects should have been equal");
        RomanInteger.disableCache();
    }

    @Test
    public void equals_SameArabic_SameRoman_ShouldReturnTrue() {
        RomanInteger romanInteger = new RomanInteger("IX", "9");
        RomanInteger another = new RomanInteger("IX", "9");
        assertEquals(romanInteger, another, "Roman integers were not equal");
    }

    @Test
    public void equals_SameArabic_DiffRoman_ShouldReturnFalse() {
        RomanInteger romanInteger = new RomanInteger("IX", "9");
        RomanInteger another = new RomanInteger("VIIII", "9");
        assertNotEquals(romanInteger, another, "Roman integers were not equal");
    }

    @Test
    public void equals_DiffArabic_DiffRoman_ShouldReturnFalse() {
        RomanInteger romanInteger = new RomanInteger("IX", "9");
        RomanInteger another = new RomanInteger("XXX", "30");
        assertNotEquals(romanInteger, another, "Roman integers were equals, despite representing different values");
    }
}
