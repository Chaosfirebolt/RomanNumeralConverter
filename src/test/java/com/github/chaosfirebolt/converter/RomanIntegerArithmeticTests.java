package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.ArithmeticMode;
import com.github.chaosfirebolt.converter.constants.IntegerType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by ChaosFire on 11/30/2021
 */
public class RomanIntegerArithmeticTests {

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
}