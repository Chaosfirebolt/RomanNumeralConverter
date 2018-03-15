package com.chaos.converter.parser.impl;

import com.chaos.converter.util.DataTransferObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ChaosFire on 14.3.2018 г.
 */
public class ArabicParserTests {

    private ArabicParser arabicParser;

    public ArabicParserTests() {
        this.arabicParser = new ArabicParser();
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test1() {
        String input = "1776";

        String expected = "MDCCLXXVI";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test2() {
        String input = "1954";

        String expected = "MCMLIV";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test3() {
        String input = "1990";

        String expected = "MCMXC";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test4() {
        String input = "2014";

        String expected = "MMXIV";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test5() {
        String input = "39";

        String expected = "XXXIX";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test6() {
        String input = "246";

        String expected = "CCXLVI";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test7() {
        String input = "207";

        String expected = "CCVII";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test8() {
        String input = "1066";

        String expected = "MLXVI";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test9() {
        String input = "3498";

        String expected = "MMMCDXCVIII";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_intValueTooLow_ShouldThrowException() {
        String input = "-1";
        this.arabicParser.parse(input);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInput_intValueTooHigh_ShouldThrowException() {
        String input = "4125";
        this.arabicParser.parse(input);
    }

    @Test(expected = NumberFormatException.class)
    public void invalidInput_WrongNumberFormat_ShouldThrowException_Test1() {
        String input = "MX";
        this.arabicParser.parse(input);
    }

    @Test(expected = NumberFormatException.class)
    public void invalidInput_WrongNumberFormat_ShouldThrowException_Test2() {
        String input = "f";
        this.arabicParser.parse(input);
    }

    @Test(expected = NumberFormatException.class)
    public void invalidInput_WrongNumberFormat_ShouldThrowException_Test3() {
        String input = "";
        this.arabicParser.parse(input);
    }
}