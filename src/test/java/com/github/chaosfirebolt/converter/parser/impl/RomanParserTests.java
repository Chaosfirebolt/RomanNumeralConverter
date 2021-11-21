package com.github.chaosfirebolt.converter.parser.impl;

import com.github.chaosfirebolt.converter.util.DataTransferObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ChaosFire on 15-Mar-18
 */
public class RomanParserTests {

    private final RomanParser romanParser;

    public RomanParserTests() {
        this.romanParser = new RomanParser();
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test1() {
        String input = "MDCCLXxVI";

        Integer expected = 1776;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test2() {
        String input = "McMLIV";

        Integer expected = 1954;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test3() {
        String input = "MCMXC";

        Integer expected = 1990;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test4() {
        String input = "MMXIV";

        Integer expected = 2014;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test5() {
        String input = "XXXIX";

        Integer expected = 39;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test6() {
        String input = "CCXLVI";

        Integer expected = 246;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test7() {
        String input = "CCvII";

        Integer expected = 207;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test8() {
        String input = "MLXVI";

        Integer expected = 1066;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test9() {
        String input = "IIII";

        Integer expected = 4;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test10() {
        String input = "VIIiI";

        Integer expected = 9;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test11() {
        String input = "XCIX";

        Integer expected = 99;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test12() {
        String input = "IC";

        Integer expected = 99;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test13() {
        String input = " XIIX";

        Integer expected = 18;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test14() {
        String input = "IIXX ";

        Integer expected = 18;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test15() {
        String input = "IIIIII ";

        Integer expected = 6;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test16() {
        String input = "XXXXXX ";

        Integer expected = 60;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test17() {
        String input = "CM ";

        Integer expected = 900;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test18() {
        String input = "MDCCCCX ";

        Integer expected = 1910;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test19() {
        String input = "MCMX ";

        Integer expected = 1910;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test20() {
        String input = "MDCDIII";

        Integer expected = 1903;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test
    public void validInput_ShouldReturnCorrectValue_Test21() {
        String input = "MCMIII";

        Integer expected = 1903;
        DataTransferObject result = this.romanParser.parse(input);
        assertEquals(expected, result.getArabic());
    }

    @Test(expected = NumberFormatException.class)
    public void invalidInput_ShouldThrowException_Test1() {
        String input = "M M";
        this.romanParser.parse(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_ShouldThrowException_Test2() {
        String input = "MmMM";
        this.romanParser.parse(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_ShouldThrowException_Test3() {
        String input = "MmMDD";
        this.romanParser.parse(input);
    }

    @Test(expected = NumberFormatException.class)
    public void invalidInput_ShouldThrowException_Test4() {
        String input = "D5";
        this.romanParser.parse(input);
    }

    @Test(expected = NumberFormatException.class)
    public void invalidInput_ShouldThrowException_Test5() {
        String input = "";
        this.romanParser.parse(input);
    }

    @Test(expected = NumberFormatException.class)
    public void invalidInput_ShouldThrowException_Test6() {
        String input = "W";
        this.romanParser.parse(input);
    }
}