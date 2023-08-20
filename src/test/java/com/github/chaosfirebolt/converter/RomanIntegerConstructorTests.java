package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.ArithmeticMode;
import com.github.chaosfirebolt.converter.testUtil.Constants;
import com.github.chaosfirebolt.converter.testUtil.FieldAccessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class RomanIntegerConstructorTests {

    @Test
    public void StringIntegerCtor_ValidInput_ShouldReturnCorrect_Test1() {
        String roman = "XL";
        int arabic = 40;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString(), "Roman representation not as expected");
        assertEquals(arabic, romanInteger.getArabic(), "arabic representation not as expected");
    }

    @Test
    public void StringIntegerCtor_ValidInput_ShouldReturnCorrect_Test2() {
        String roman = "XXXX";
        int arabic = 40;
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString(), "Roman representation not as expected");
        assertEquals(arabic, romanInteger.getArabic(), "Arabic representation not as expected");
    }

    @Test
    public void StringIntegerCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test1() {
        String roman = "XX";
        int arabic = 40;
        assertExceptionThrown(IllegalArgumentException.class, () -> new RomanInteger(roman, arabic));
    }

    private static void assertExceptionThrown(Class<? extends Exception> expectedException, Executable executable) {
        Exception exception = assertThrows(expectedException, executable, () -> String.format("%s expected, but was not thrown", expectedException.getSimpleName()));
        String message = exception.getMessage();
        assertTrue(message != null && !message.isEmpty());
    }

    @Test
    public void StringIntegerCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test2() {
        String roman = "XL";
        int arabic = 30;
        assertExceptionThrown(IllegalArgumentException.class, () -> new RomanInteger(roman, arabic));
    }

    @Test
    public void StringIntegerCtor_InvalidRoman_ShouldThrowException() {
        String roman = "h";
        int arabic = 5;
        assertExceptionThrown(NumberFormatException.class, () -> new RomanInteger(roman, arabic));
    }

    @Test
    public void StringIntegerCtor_InvalidInteger_ShouldThrowException() {
        String roman = "MMMMM";
        int arabic = 5000;
        assertExceptionThrown(IllegalArgumentException.class, () -> new RomanInteger(roman, arabic));
    }

    @Test
    public void StringStringCtor_ValidInput_ShouldReturnCorrect_Test1() {
        String roman = "XL";
        String arabic = "40";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, Integer.toString(romanInteger.getArabic()));
    }

    @Test
    public void StringStringCtor_ValidInput_ShouldReturnCorrect_Test2() {
        String roman = "XXXX";
        String arabic = "40";
        RomanInteger romanInteger = new RomanInteger(roman, arabic);

        assertEquals(roman, romanInteger.toString());
        assertEquals(arabic, Integer.toString(romanInteger.getArabic()));
    }

    @Test
    public void StringStringCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test1() {
        String roman = "XX";
        String arabic = "40";
        assertExceptionThrown(IllegalArgumentException.class, () -> new RomanInteger(roman, arabic));
    }

    @Test
    public void StringStringCtor_RomanAndArabicRepresentDiffValues_ShouldThrowException_Test2() {
        String roman = "XL";
        String arabic = "30";
        assertExceptionThrown(IllegalArgumentException.class, () -> new RomanInteger(roman, arabic));
    }

    @Test
    public void StringStringCtor_InvalidRoman_ShouldThrowException() {
        String roman = "h";
        String arabic = "5";
        assertExceptionThrown(NumberFormatException.class, () -> new RomanInteger(roman, arabic));
    }

    @Test
    public void StringStringCtor_InvalidInteger_ShouldThrowException() {
        String roman = "MMMMM";
        String arabic = "5000";
        assertExceptionThrown(IllegalArgumentException.class, () -> new RomanInteger(roman, arabic));
    }

    @Test
    public void CopyConstructor_ShouldReturnDifferentObject() {
        RomanInteger source = RomanInteger.FIFTY;
        RomanInteger copy = new RomanInteger(source);
        assertNotSame(source, copy);
    }

    @Test
    public void CopyConstructor_LooseMode_ValuesShouldBeTheSame() {
        RomanInteger source = RomanInteger.FIFTY;
        RomanInteger copy = new RomanInteger(source);
        assertEqualFieldValues(source, copy);
    }

    @Test
    public void CopyConstructor_StrictMode_ValuesShouldBeTheSame() {
        RomanInteger source = RomanInteger.FIFTY.setArithmeticMode(ArithmeticMode.STRICT);
        RomanInteger copy = new RomanInteger(source);
        assertEqualFieldValues(source, copy);
    }

    private static void assertEqualFieldValues(RomanInteger source, RomanInteger copy) {
        for (String fieldName : Constants.FIELD_NAMES) {
            Object expected = FieldAccessor.getValue(source, fieldName);
            Object actual = FieldAccessor.getValue(copy, fieldName);
            assertEquals(expected, actual, () -> String.format("Value for field '%s' not as expected - ", fieldName));
        }
    }
}