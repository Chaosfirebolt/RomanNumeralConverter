package com.github.chaosfirebolt.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RomanIntegerCompareToTests {

    private final RomanInteger first;
    private final RomanInteger second;
    private final int expectedResult;

    public RomanIntegerCompareToTests(RomanInteger first, RomanInteger second, int expectedResult) {
        this.first = first;
        this.second = second;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[]{ RomanInteger.ONE, RomanInteger.FIVE, -1 },
                             new Object[]{ RomanInteger.THOUSAND, RomanInteger.FIFTY, 1 },
                             new Object[]{ RomanInteger.parse("11"), RomanInteger.parse("19"), -1 },
                             new Object[]{ RomanInteger.parse("15"), RomanInteger.parse("9"), 1 },
                             new Object[]{ RomanInteger.parse("XXX"), RomanInteger.parse("XL"), -1 },
                             new Object[]{ RomanInteger.parse("L"), RomanInteger.parse("LX"), -1 },
                             new Object[]{ RomanInteger.parse("MMCDXLVII"), RomanInteger.parse("MMCCXCI"), 1 },
                             new Object[]{ RomanInteger.parse("MMCDXLVII"), RomanInteger.parse("MMDCCXCIV"), -1 },
                             new Object[]{ RomanInteger.THOUSAND, RomanInteger.THOUSAND, 0 },
                             new Object[]{ RomanInteger.parse("XXV"), RomanInteger.parse("XXV"), 0 },
                             new Object[]{ RomanInteger.parse("MMCDXLVII"), RomanInteger.parse("2447"), 0 },
                             new Object[]{ RomanInteger.parse("IV"), RomanInteger.parse("IIII"), 0 },
                             new Object[]{ RomanInteger.parse("XV"), RomanInteger.TEN, 1 },
                             new Object[]{ RomanInteger.TEN, RomanInteger.parse("XV"), -1 });
    }

    @Test
    public void test() {
        int actualResult = first.compareTo(second);
        assertEquals("Comparison result not as expected", expectedResult, actualResult);
    }
}
