package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RomanIntegerParseTests {

    @Test
    public void parseStringParam_ValidInputArabic_ShouldReturnCorrect() {
        int input = 19;
        RomanInteger result = RomanInteger.parse(Integer.toString(input));
        assertEquals(input, result.getArabic());
        String expectedRoman = "XIX";
        assertEquals(expectedRoman, result.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseStringParam_TooLowInputArabic_ShouldThrowException() {
        int input = -19;
        RomanInteger.parse(Integer.toString(input));
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseStringParam_TooHighInputArabic_ShouldThrowException() {
        int input = 4263;
        RomanInteger.parse(Integer.toString(input));
    }

    @Test
    public void parseStringParam_ValidRomanInput_ShouldReturnCorrect() {
        String input = "XxIV";
        RomanInteger result = RomanInteger.parse(input);

        String expectedRoman = input.toUpperCase();
        assertEquals(expectedRoman, result.toString());
        int expectedArabic = 24;
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
        int input = 16;
        RomanInteger result = RomanInteger.parse(Integer.toString(input), IntegerType.ARABIC);
        String expectedRoman = "XVI";
        assertEquals(expectedRoman, result.toString());
        assertEquals(input, result.getArabic());
    }

    @Test(expected = NumberFormatException.class)
    public void parseStringIntegerTypeParam_InvalidIntegerType_ShouldThrowException() {
        int input = 16;
        RomanInteger.parse(Integer.toString(input), IntegerType.ROMAN);
    }


    @Test(expected = NumberFormatException.class)
    public void parseStringIntegerTypeParam_InvalidIntegerType_Test2_ShouldThrowException() {
        String input = "XXX";
        RomanInteger.parse(input, IntegerType.ARABIC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseStringIntegerTypeParam_TooLowInputArabic_ShouldThrowException() {
        int input = 0;
        RomanInteger.parse(Integer.toString(input), IntegerType.ARABIC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseStringIntegerTypeParam_TooHighInputArabic_ShouldThrowException() {
        int input = 5000;
        RomanInteger.parse(Integer.toString(input), IntegerType.ARABIC);
    }

    @Test
    public void parseStringIntegerTypeParam_ValidInputRoman_ShouldReturnCorrect() {
        String input = "MV";
        RomanInteger result = RomanInteger.parse(input, IntegerType.ROMAN);
        assertEquals(input, result.toString());
        int expectedArabic = 1005;
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
}