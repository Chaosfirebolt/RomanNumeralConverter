package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.ArithmeticMode;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ChaosFire on 11/30/2021
 */
public class RomanIntegerSetArithmeticModeTests {

    @Test
    public void sourceIsLoose_setToLoose_ShouldReturnSameInstance() {
        RomanInteger source = RomanInteger.HUNDRED;
        RomanInteger result = source.setArithmeticMode(ArithmeticMode.LOOSE);
        assertSame(source, result);
    }

    @Test
    public void sourceIsLoose_setToStrict_ShouldReturnAnotherInstanceForSameRomanInteger() {
        RomanInteger source = RomanInteger.HUNDRED;
        RomanInteger result = source.setArithmeticMode(ArithmeticMode.STRICT);
        assertNotSame(source, result);
        assertEquals(source.getArabic(), result.getArabic());
        assertEquals(source.toString(), result.toString());
        assertEquals(source.hashCode(), result.hashCode());
    }

    @Test
    public void sourceIsStrict_setToStrict_ShouldReturnSameInstance() {
        RomanInteger source = RomanInteger.HUNDRED.setArithmeticMode(ArithmeticMode.STRICT);
        RomanInteger result = source.setArithmeticMode(ArithmeticMode.STRICT);
        assertSame(source, result);
    }

    @Test
    public void sourceIsStrict_setToLoose_ShouldReturnAnotherInstanceForSameRomanInteger() {
        RomanInteger source = RomanInteger.HUNDRED.setArithmeticMode(ArithmeticMode.STRICT);
        RomanInteger result = source.setArithmeticMode(ArithmeticMode.LOOSE);
        assertNotSame(source, result);
        assertEquals(source.getArabic(), result.getArabic());
        assertEquals(source.toString(), result.toString());
        assertEquals(source.hashCode(), result.hashCode());
    }
}