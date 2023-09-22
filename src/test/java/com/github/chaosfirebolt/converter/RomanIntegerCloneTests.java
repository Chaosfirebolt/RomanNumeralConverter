package com.github.chaosfirebolt.converter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RomanIntegerCloneTests {

    @Test
    public void clone_ShouldReturnDifferentObject() {
        RomanInteger source = RomanInteger.FIFTY;
        RomanInteger copy = source.clone();
        assertNotSame(source, copy, "Clone returned same instance");
    }

    @Test
    public void clone_ValuesShouldBeTheSame() {
        RomanInteger source = RomanInteger.FIFTY;
        RomanInteger copy = source.clone();
        assertEquals(source.getArabic(), copy.getArabic(), "Arabic value not as expected");
        assertEquals(source.getRoman(), copy.getRoman(), "Roman value not as expected");
    }

    @Test
    public void clone_ObjectsShouldBeEqual() {
        RomanInteger source = RomanInteger.FIFTY;
        RomanInteger copy = source.clone();
        assertEquals(source, copy, "Copy should be equal to source");
    }
}
