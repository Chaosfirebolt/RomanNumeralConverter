package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.ArithmeticMode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RomanIntegerSetArithmeticModeTests {

    @Test
    public void sourceIsLoose_setToLoose_ShouldReturnSameInstance() {
        RomanInteger source = RomanInteger.HUNDRED;
        RomanInteger result = source.setArithmeticMode(ArithmeticMode.LOOSE);
        assertSame(source, result, "Expected same instance, but was different");
    }

    @Test
    public void sourceIsLoose_setToStrict_ShouldReturnAnotherInstanceForSameRomanInteger() {
        RomanInteger source = RomanInteger.HUNDRED;
        RomanInteger result = source.setArithmeticMode(ArithmeticMode.STRICT);
        assertNotSame(source, result, "Expected different instance, but was same");
        assertEquals(source.getArabic(), result.getArabic(), "Arabic representation not as expected");
        assertEquals(source.toString(), result.toString(), "Roman representation not as expected");
        assertEquals(source.hashCode(), result.hashCode(), "Hash code not as expected");
    }

    @Test
    public void sourceIsStrict_setToStrict_ShouldReturnSameInstance() {
        RomanInteger source = RomanInteger.HUNDRED.setArithmeticMode(ArithmeticMode.STRICT);
        RomanInteger result = source.setArithmeticMode(ArithmeticMode.STRICT);
        assertSame(source, result, "Expected same instance, but was different");
    }

    @Test
    public void sourceIsStrict_setToLoose_ShouldReturnAnotherInstanceForSameRomanInteger() {
        RomanInteger source = RomanInteger.HUNDRED.setArithmeticMode(ArithmeticMode.STRICT);
        RomanInteger result = source.setArithmeticMode(ArithmeticMode.LOOSE);
        assertNotSame(source, result, "Expected different instance, but was same");
        assertEquals(source.getArabic(), result.getArabic(), "Arabic representation not as expected");
        assertEquals(source.toString(), result.toString(), "Roman representation not as expected");
        assertEquals(source.hashCode(), result.hashCode(), "Hash code not as expected");
    }
}