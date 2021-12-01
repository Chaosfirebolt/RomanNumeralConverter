package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.ArithmeticMode;
import com.github.chaosfirebolt.converter.constants.IntegerType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by ChaosFire on 11/30/2021
 */
public class RomanIntegerArithmeticOperationsTests {

    //add tests
    @Test
    public void add_ValidResult_ShouldReturnCorrect() {
        int first = 15;
        int second = 20;

        RomanInteger expected = RomanInteger.parse(Integer.toString(first + second), IntegerType.ARABIC);
        assertEquals(expected, add(first, second));
    }

    @Test
    public void add_TooHighResult_LooseMode_ShouldReturnNull() {
        assertNull(add(2020, 2000));
    }

    private static RomanInteger add(int first, int second) {
        return RomanInteger.parse(Integer.toString(first), IntegerType.ARABIC).add(RomanInteger.parse(Integer.toString(second), IntegerType.ARABIC));
    }

    @Test(expected = ArithmeticException.class)
    public void add_TooHighResult_FirstIsStrictMode_ShouldThrowArithmeticException() {
        String number = "2000";
        RomanInteger.parse(number).setArithmeticMode(ArithmeticMode.STRICT).add(RomanInteger.parse(number));
    }

    @Test(expected = ArithmeticException.class)
    public void add_TooHighResult_SecondIsStrictMode_ShouldThrowArithmeticException() {
        String number = "2000";
        RomanInteger.parse(number).add(RomanInteger.parse(number).setArithmeticMode(ArithmeticMode.STRICT));
    }

    @Test(expected = ArithmeticException.class)
    public void add_TooHighResult_BothAreStrictMode_ShouldThrowArithmeticException() {
        String number = "2000";
        RomanInteger.parse(number).setArithmeticMode(ArithmeticMode.STRICT).add(RomanInteger.parse(number).setArithmeticMode(ArithmeticMode.STRICT));
    }

    //subtract tests
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

    @Test(expected = ArithmeticException.class)
    public void subtract_ResultTooLow_FirstStrict_ShouldThrowException() {
        RomanInteger.FIFTY.setArithmeticMode(ArithmeticMode.STRICT).subtract(RomanInteger.HUNDRED);
    }

    @Test(expected = ArithmeticException.class)
    public void subtract_ResultTooLow_SecondStrict_ShouldThrowException() {
        RomanInteger.FIFTY.subtract(RomanInteger.HUNDRED.setArithmeticMode(ArithmeticMode.STRICT));
    }

    @Test(expected = ArithmeticException.class)
    public void subtract_ResultTooLow_BothStrict_ShouldThrowException() {
        RomanInteger.FIFTY.setArithmeticMode(ArithmeticMode.STRICT).subtract(RomanInteger.HUNDRED.setArithmeticMode(ArithmeticMode.STRICT));
    }

    //multiply tests
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

    @Test(expected = ArithmeticException.class)
    public void multiply_ResultTooHigh_FirstStrict_ShouldThrowException() {
        RomanInteger.THOUSAND.setArithmeticMode(ArithmeticMode.STRICT).multiply(RomanInteger.TEN);
    }

    @Test(expected = ArithmeticException.class)
    public void multiply_ResultTooHigh_SecondStrict_ShouldThrowException() {
        RomanInteger.THOUSAND.multiply(RomanInteger.TEN.setArithmeticMode(ArithmeticMode.STRICT));
    }
    @Test(expected = ArithmeticException.class)
    public void multiply_ResultTooHigh_BothStrict_ShouldThrowException() {
        RomanInteger.THOUSAND.setArithmeticMode(ArithmeticMode.STRICT).multiply(RomanInteger.TEN.setArithmeticMode(ArithmeticMode.STRICT));
    }

    //divide tests
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

    @Test(expected = ArithmeticException.class)
    public void divide_ResultTooLow_FirstStrict_ShouldThrowException() {
        RomanInteger.parse("15", IntegerType.ARABIC).setArithmeticMode(ArithmeticMode.STRICT).divide(RomanInteger.FIFTY);
    }

    @Test(expected = ArithmeticException.class)
    public void divide_ResultTooLow_SecondStrict_ShouldThrowException() {
        RomanInteger.parse("15", IntegerType.ARABIC).divide(RomanInteger.FIFTY.setArithmeticMode(ArithmeticMode.STRICT));
    }

    @Test(expected = ArithmeticException.class)
    public void divide_ResultTooLow_BothStrict_ShouldThrowException() {
        RomanInteger.parse("15", IntegerType.ARABIC).setArithmeticMode(ArithmeticMode.STRICT).divide(RomanInteger.FIFTY.setArithmeticMode(ArithmeticMode.STRICT));
    }

    //remainder tests
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

    @Test(expected = ArithmeticException.class)
    public void remainder_ResultTooLow_FirstStrict_ShouldThrowException() {
        RomanInteger.TEN.setArithmeticMode(ArithmeticMode.STRICT).remainder(RomanInteger.FIVE);
    }

    @Test(expected = ArithmeticException.class)
    public void remainder_ResultTooLow_SecondStrict_ShouldThrowException() {
        RomanInteger.TEN.remainder(RomanInteger.FIVE.setArithmeticMode(ArithmeticMode.STRICT));
    }

    @Test(expected = ArithmeticException.class)
    public void remainder_ResultTooLow_BothStrict_ShouldThrowException() {
        RomanInteger.TEN.setArithmeticMode(ArithmeticMode.STRICT).remainder(RomanInteger.FIVE.setArithmeticMode(ArithmeticMode.STRICT));
    }
}