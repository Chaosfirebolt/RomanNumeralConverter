package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ChaosFire on 15-Mar-18
 */
public class RomanIntegerTests {

    public RomanIntegerTests() {
    }

    @Test
    public void StringIntegerCtor_ValidInput_ShouldReturnCorrect_Test1() {
        String roman = "XL";
        Integer arabic = 40;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test
    public void StringIntegerCtor_ValidInput_ShouldReturnCorrect_Test2() {
        String roman = "XXXX";
        Integer arabic = 40;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringIntegerCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test1() {
        String roman = "XX";
        Integer arabic = 40;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringIntegerCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test2() {
        String roman = "XL";
        Integer arabic = 30;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test(expected = NumberFormatException.class)
    public void StringIntegerCtor_InvalidRoman_ShouldThrowException() {
        String roman = "h";
        Integer arabic = 5;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);
        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringIntegerCtor_InvalidInteger_ShouldThrowException() {
        String roman = "MMMMM";
        Integer arabic = 5000;
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
        assertEquals(arabic, romanInteger.getArabic().toString());
    }

    @Test
    public void StringStringCtor_ValidInput_ShouldReturnCorrect_Test2() {
        String roman = "XXXX";
        String arabic = "40";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringStringCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test1() {
        String roman = "XX";
        String arabic = "40";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringStringCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test2() {
        String roman = "XL";
        String arabic = "30";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic().toString());
    }

    @Test(expected = NumberFormatException.class)
    public void StringStringCtor_InvalidRoman_ShouldThrowException() {
        String roman = "h";
        String arabic = "5";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);
        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void StringStringCtor_InvalidInteger_ShouldThrowException() {
        String roman = "MMMMM";
        String arabic = "5000";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);
        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, romanInteger.getArabic().toString());
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void equals_AnotherObject_ShouldReturnFalse() {
        Integer ten = 10;
        assertFalse(RomanInteger.TEN.equals(ten));
    }

    @Test
    public void equals_SameArabic_SameRoman_ShouldReturnTrue() {
        RomanInteger romanInteger = new RomanInteger("IX", 9);
        RomanInteger another = new RomanInteger("IX", 9);
        assertTrue(romanInteger.equals(another));
    }

    @Test
    public void equals_SameArabic_DiffRoman_ShouldReturnTrue() {
        RomanInteger romanInteger = new RomanInteger("IX", 9);
        RomanInteger another = new RomanInteger("VIIII", 9);
        assertTrue(romanInteger.equals(another));
    }

    @Test
    public void equals_DiffArabic_DiffRoman_ShouldReturnFalse() {
        RomanInteger romanInteger = new RomanInteger("IX", 9);
        RomanInteger another = new RomanInteger("XXX", 30);
        assertFalse(romanInteger.equals(another));
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
    public void parseStringParam_ValidInputArabic_ShouldReturnCorrect() {
        Integer input = 19;
        RomanInteger result = RomanInteger.parse(input.toString());
        assertEquals(input, result.getArabic());
        String expectedRoman = "XIX";
        assertEquals(expectedRoman, result.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseStringParam_TooLowInputArabic_ShouldThrowException() {
        Integer input = -19;
        RomanInteger.parse(input.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseStringParam_TooHighInputArabic_ShouldThrowException() {
        Integer input = 4263;
        RomanInteger.parse(input.toString());
    }

    @Test
    public void parseStringParam_ValidRomanInput_ShouldReturnCorrect() {
        String input = "XxIV";
        RomanInteger result = RomanInteger.parse(input);

        String expectedRoman = input.toUpperCase();
        assertEquals(expectedRoman, result.toString());
        Integer expectedArabic = 24;
        assertEquals(expectedArabic, result.getArabic());
    }

    @Test(expected = NumberFormatException.class)
    public void parseStringParam_InvalidRomanInput_ShouldReturnCorrect_Test1() {
        String input = "X IV";
        RomanInteger.parse(input);
    }

    @Test(expected = NumberFormatException.class)
    public void parseStringParam_InvalidRomanInput_ShouldReturnCorrect_Test2() {
        String input = "Vh";
        RomanInteger.parse(input);
    }

    @Test
    public void parseStringIntegerTypeParam_ValidInput_ShouldReturnCorrect() {
        Integer input = 16;
        RomanInteger result = RomanInteger.parse(input.toString(), IntegerType.ARABIC);
        String expectedRoman = "XVI";
        assertEquals(expectedRoman, result.toString());
        assertEquals(input, result.getArabic());
    }

    @Test(expected = NumberFormatException.class)
    public void parseStringIntegerTypeParam_InvalidIntegerType_ShouldThrowException() {
        Integer input = 16;
        RomanInteger.parse(input.toString(), IntegerType.ROMAN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseStringIntegerTypeParam_TooLowInputArabic_ShouldThrowException() {
        Integer input = 0;
        RomanInteger.parse(input.toString(), IntegerType.ARABIC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseStringIntegerTypeParam_TooHighInputArabic_ShouldThrowException() {
        Integer input = 5000;
        RomanInteger.parse(input.toString(), IntegerType.ARABIC);
    }

    @Test
    public void parseStringIntegerTypeParam_ValidInputRoman_ShouldReturnCorrect() {
        String input = "MV";
        RomanInteger result = RomanInteger.parse(input, IntegerType.ROMAN);
        assertEquals(input, result.toString());
        Integer expectedArabic = 1005;
        assertEquals(expectedArabic, result.getArabic());
    }

    @Test(expected = NumberFormatException.class)
    public void parseStringIntegerTypeParam_InvalidInputRoman_ShouldThrowException() {
        String input = "MV0";
        RomanInteger.parse(input, IntegerType.ROMAN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseStringIntegerTypeParam_TooHighInputRoman_ShouldThrowException() {
        String input = "MMMDDD";
        RomanInteger.parse(input, IntegerType.ROMAN);
    }

    @Test
    public void hashCode_DiffObjects_SameArabic_DiffRoman_ShouldReturnSameHashCodeValues() {
        RomanInteger romanInteger = new RomanInteger("DCCCC", 900);
        RomanInteger another = new RomanInteger("CM", 900);
        assertEquals(romanInteger.hashCode(), another.hashCode());
    }

    @Test
    public void add_ValidResult_ShouldReturnCorrect() {
        Integer first = 15;
        Integer second = 20;

        RomanInteger expected = RomanInteger.parse(Integer.toString(first + second), IntegerType.ARABIC);
        assertEquals(expected, this.add(first, second));
    }

    @Test
    public void add_TooHighResult_ShouldReturnNull() {
        assertNull(this.add(2020, 2000));
    }

    private RomanInteger add(Integer first, Integer second) {
        return RomanInteger.parse(first.toString(), IntegerType.ARABIC).add(RomanInteger.parse(second.toString(), IntegerType.ARABIC));
    }

    @Test
    public void subtract_ValidResult_ShouldReturnCorrect() {
        RomanInteger expected = RomanInteger.parse("90", IntegerType.ARABIC);
        assertEquals(expected, RomanInteger.HUNDRED.subtract(RomanInteger.TEN));
    }

    @Test
    public void subtract_ResultTooLow_ShouldReturnNull_Test1() {
        assertNull(RomanInteger.TEN.subtract(RomanInteger.TEN));
    }

    @Test
    public void subtract_ResultTooLow_ShouldReturnNull_Test2() {
        assertNull(RomanInteger.FIFTY.subtract(RomanInteger.HUNDRED));
    }

    @Test
    public void multiply_ValidResult_ShouldReturnCorrect() {
        Integer first = 15;
        Integer second = 20;
        RomanInteger expected = RomanInteger.parse(Integer.toString(first * second), IntegerType.ARABIC);
        RomanInteger actual = RomanInteger.parse(first.toString(), IntegerType.ARABIC).multiply(RomanInteger.parse(second.toString(), IntegerType.ARABIC));
        assertEquals(expected, actual);
    }

    @Test
    public void multiply_ResultTooHigh_ShouldReturnNull() {
        assertNull(RomanInteger.THOUSAND.multiply(RomanInteger.TEN));
    }

    @Test
    public void divide_ValidResult_ShouldReturnCorrect_Test1() {
        RomanInteger expected = RomanInteger.parse("2", IntegerType.ARABIC);
        RomanInteger actual = RomanInteger.HUNDRED.divide(RomanInteger.FIFTY);
        assertEquals(expected, actual);
    }

    @Test
    public void divide_ValidResult_ShouldReturnCorrect_Test2() {
        RomanInteger expected = RomanInteger.parse("2", IntegerType.ARABIC);
        RomanInteger actual = RomanInteger.FIFTY.divide(RomanInteger.parse("20", IntegerType.ARABIC));
        assertEquals(expected, actual);
    }

    @Test
    public void divide_ResultTooLow_ShouldReturnNull_Test1() {
        assertNull(RomanInteger.parse("40", IntegerType.ARABIC).divide(RomanInteger.FIFTY));
    }

    @Test
    public void divide_ResultTooLow_ShouldReturnNull_Test2() {
        assertNull(RomanInteger.parse("15", IntegerType.ARABIC).divide(RomanInteger.FIFTY));
    }

    @Test
    public void remainder_ValidResult_ShouldReturnCorrect() {
        RomanInteger expected = RomanInteger.FIVE;
        RomanInteger actual = RomanInteger.parse("328", IntegerType.ARABIC).remainder(RomanInteger.parse("19", IntegerType.ARABIC));
        assertEquals(expected, actual);
    }

    @Test
    public void remainder_ResultTooLow_ShouldReturnNull() {
        assertNull(RomanInteger.TEN.remainder(RomanInteger.FIVE));
    }
}