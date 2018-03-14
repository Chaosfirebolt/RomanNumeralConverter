package com.chaos.converter.parser.impl;

import com.chaos.converter.RomanInteger;
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
        RomanInteger result = this.arabicParser.parse(input);
        assertEquals(expected, result.toString());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test2() {
        String input = "1954";

        String expected = "MCMLIV";
        RomanInteger result = this.arabicParser.parse(input);
        assertEquals(expected, result.toString());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test3() {
        String input = "1990";

        String expected = "MCMXC";
        RomanInteger result = this.arabicParser.parse(input);
        assertEquals(expected, result.toString());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test4() {
        String input = "2014";

        String expected = "MMXIV";
        RomanInteger result = this.arabicParser.parse(input);
        assertEquals(expected, result.toString());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test5() {
        String input = "39";

        String expected = "XXXIX";
        RomanInteger result = this.arabicParser.parse(input);
        assertEquals(expected, result.toString());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test6() {
        String input = "246";

        String expected = "CCXLVI";
        RomanInteger result = this.arabicParser.parse(input);
        assertEquals(expected, result.toString());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test7() {
        String input = "207";

        String expected = "CCVII";
        RomanInteger result = this.arabicParser.parse(input);
        assertEquals(expected, result.toString());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test8() {
        String input = "1066";

        String expected = "MLXVI";
        RomanInteger result = this.arabicParser.parse(input);
        assertEquals(expected, result.toString());
    }
}