package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.ArithmeticMode;
import com.github.chaosfirebolt.converter.testUtil.Constants;
import com.github.chaosfirebolt.converter.testUtil.FieldAccessor;
import org.junit.Test;

import static org.junit.Assert.*;

public class RomanIntegerTests {

    @Test
    public void equals_AnotherObject_ShouldReturnFalse() {
        Object ten = 10;
        assertNotEquals(RomanInteger.TEN, ten);
    }

    @Test
    public void equals_SameArabic_SameRoman_ShouldReturnTrue() {
        RomanInteger romanInteger = new RomanInteger("IX", 9);
        RomanInteger another = new RomanInteger("IX", 9);
        assertEquals(romanInteger, another);
    }

    @Test
    public void equals_SameArabic_DiffRoman_ShouldReturnTrue() {
        RomanInteger romanInteger = new RomanInteger("IX", 9);
        RomanInteger another = new RomanInteger("VIIII", 9);
        assertEquals(romanInteger, another);
    }

    @Test
    public void equals_DiffArabic_DiffRoman_ShouldReturnFalse() {
        RomanInteger romanInteger = new RomanInteger("IX", 9);
        RomanInteger another = new RomanInteger("XXX", 30);
        assertNotEquals(romanInteger, another);
    }

    @Test
    public void compareTo_ShouldReturnCorrect_Test1() {
        RomanInteger romanInteger = new RomanInteger("IV", 4);
        RomanInteger another = new RomanInteger("IIII", 4);
        int expected = 0;
        int actual = romanInteger.compareTo(another);
        assertEquals(expected, actual);
    }

    @Test
    public void compareTo_ShouldReturnCorrect_Test2() {
        RomanInteger romanInteger = new RomanInteger("XV", 15);
        int expected = 1;
        int actual = romanInteger.compareTo(RomanInteger.TEN);
        assertEquals(expected, actual);
    }

    @Test
    public void compareTo_ShouldReturnCorrect_Test3() {
        RomanInteger romanInteger = new RomanInteger("XV", 15);
        int expected = -1;
        int actual = RomanInteger.TEN.compareTo(romanInteger);
        assertEquals(expected, actual);
    }

    @Test
    public void hashCode_DiffObjects_SameArabic_DiffRoman_ShouldReturnSameHashCodeValues() {
        RomanInteger romanInteger = new RomanInteger("DCCCC", 900);
        RomanInteger another = new RomanInteger("CM", 900);
        assertEquals(romanInteger.hashCode(), another.hashCode());
    }

    @Test
    public void hashCode_ValueShouldNotBeDefaultAfterCall_Test1() {
        RomanInteger romanInteger = RomanInteger.parse("17");
        assertHashCodeNotDefault(romanInteger);
    }

    @Test
    public void hashCode_ValueShouldNotBeDefaultAfterCall_Test2() {
        RomanInteger romanInteger = RomanInteger.parse("19");
        assertHashCodeNotDefault(romanInteger);
    }

    @Test
    public void hashCode_ValueShouldNotBeDefaultAfterCall_Test3() {
        RomanInteger romanInteger = RomanInteger.parse("31");
        assertHashCodeNotDefault(romanInteger);
    }

    private static void assertHashCodeNotDefault(RomanInteger romanInteger) {
        int hashCode = romanInteger.hashCode();
        assertNotEquals(0, hashCode);
    }

    @Test
    public void CloneConstructor_ShouldReturnDifferentObject() {
        RomanInteger source = RomanInteger.FIFTY;
        RomanInteger copy = source.clone();
        assertNotSame(source, copy);
    }

    @Test
    public void CloneConstructor_LooseMode_ValuesShouldBeTheSame() {
        RomanInteger source = RomanInteger.FIFTY;
        RomanInteger copy = source.clone();
        assertEqualFieldValues(source, copy);
    }

    @Test
    public void CloneConstructor_StrictMode_ValuesShouldBeTheSame() {
        RomanInteger source = RomanInteger.FIFTY.setArithmeticMode(ArithmeticMode.STRICT);
        RomanInteger copy = source.clone();
        assertEqualFieldValues(source, copy);
    }

    private static void assertEqualFieldValues(RomanInteger source, RomanInteger copy) {
        for (String fieldName : Constants.FIELD_NAMES) {
            Object expected = FieldAccessor.getValue(source, fieldName);
            Object actual = FieldAccessor.getValue(copy, fieldName);
            assertEquals(String.format("Value for field '%s' not as expected - ", fieldName), expected, actual);
        }
    }
}