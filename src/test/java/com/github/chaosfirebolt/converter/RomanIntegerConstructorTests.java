package com.github.chaosfirebolt.converter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ChaosFire on 12/3/2021
 */
public class RomanIntegerConstructorTests {

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
}