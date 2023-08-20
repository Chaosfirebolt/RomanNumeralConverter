package com.github.chaosfirebolt.converter.parser.impl;

import com.github.chaosfirebolt.converter.util.DataTransferObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArabicParserTests {

    private final ArabicParser arabicParser;

    public ArabicParserTests() {
        this.arabicParser = new ArabicParser();
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test1() {
        String input = " 1776";

        String expected = "MDCCLXXVI";
        DataTransferObject result = this.arabicParser.parse(input);
        assertEquals(expected, result.getRoman());
    }

    @Test
    public void validInput_ShouldReturnCorrect_Test2() {
        String input = "1954 ";

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

    @Test
    public void invalidInput_intValueTooLow_ShouldThrowException() {
        assertNumberFormatExceptionOnInvalidInput("-1");
    }

    @Test
    public void invalidInput_intValueTooHigh_ShouldThrowException() {
        assertExceptionThrownOnInvalidInput(IllegalArgumentException.class, "4125");
    }

    @Test
    public void invalidInput_WrongNumberFormat_ShouldThrowException_Test1() {
        assertNumberFormatExceptionOnInvalidInput("MX");
    }

    @Test
    public void invalidInput_WrongNumberFormat_ShouldThrowException_Test2() {
        assertNumberFormatExceptionOnInvalidInput("f");
    }

    @Test
    public void invalidInput_WrongNumberFormat_ShouldThrowException_Test3() {
        assertNumberFormatExceptionOnInvalidInput("");
    }

    private void assertNumberFormatExceptionOnInvalidInput(String input) {
        assertExceptionThrownOnInvalidInput(NumberFormatException.class, input);
    }

    private void assertExceptionThrownOnInvalidInput(Class<? extends Exception> expectedException, String input) {
        assertThrows(expectedException, () -> this.arabicParser.parse(input), () -> expectedException.getSimpleName() + " should have been thrown for input - " + input);
    }
}