package com.github.chaosfirebolt.converter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ChaosFire on 15-Mar-18
 */
public class RomanIntegerTests {

    @Test
    public void StringIntegerCtor_ValidInput_ShouldReturnCorrect_Test1() {
        String roman = "XL";
        int arabic = 40;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test
    public void StringIntegerCtor_ValidInput_ShouldReturnCorrect_Test2() {
        String roman = "XXXX";
        int arabic = 40;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringIntegerCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test1() {
        String roman = "XX";
        int arabic = 40;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringIntegerCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test2() {
        String roman = "XL";
        int arabic = 30;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test(expected = NumberFormatException.class)
    public void StringIntegerCtor_InvalidRoman_ShouldThrowException() {
        String roman = "h";
        int arabic = 5;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);
        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringIntegerCtor_InvalidInteger_ShouldThrowException() {
        String roman = "MMMMM";
        int arabic = 5000;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);
        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test
    public void StringStringCtor_ValidInput_ShouldReturnCorrect_Test1() {
        String roman = "XL";
        String arabic = "40";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, Integer.toString(romanInteger.getArabic()));
    }

    @Test
    public void StringStringCtor_ValidInput_ShouldReturnCorrect_Test2() {
        String roman = "XXXX";
        String arabic = "40";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, Integer.toString(romanInteger.getArabic()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringStringCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test1() {
        String roman = "XX";
        String arabic = "40";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, Integer.toString(romanInteger.getArabic()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringStringCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test2() {
        String roman = "XL";
        String arabic = "30";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, Integer.toString(romanInteger.getArabic()));
    }

    @Test(expected = NumberFormatException.class)
    public void StringStringCtor_InvalidRoman_ShouldThrowException() {
        String roman = "h";
        String arabic = "5";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);
        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, Integer.toString(romanInteger.getArabic()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringStringCtor_InvalidInteger_ShouldThrowException() {
        String roman = "MMMMM";
        String arabic = "5000";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);
        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, Integer.toString(romanInteger.getArabic()));
    }

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
}