package com.github.chaosfirebolt.converter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public class RomanIntegerParseBiDirectionalCacheTests {

    @BeforeAll
    public static void setup() {
        RomanInteger.setCache(BiDirectionalMapRomanIntegerCache::new);
    }

    @AfterAll
    public static void cleanup() {
        RomanInteger.disableCache();
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 49, 123, 2751, 3591 })
    public void parseArabic_ThenParseRoman_ShouldReturnSameInstance(int arabic) {
        String arabicString = Integer.toString(arabic);
        parseAndAssert(arabicString);
    }

    private void parseAndAssert(String value) {
        RomanInteger firstParseResult = RomanInteger.parse(value);
        RomanInteger secondParseResult = RomanInteger.parse(value);
        assertSame(firstParseResult, secondParseResult, "Different instance returned after parsing same arabic value");

        RomanInteger thirdParseResult = RomanInteger.parse(firstParseResult.getRoman());
        assertSame(firstParseResult, thirdParseResult, "Different instance returned after parsing roman value");
    }

    @ParameterizedTest
    @ValueSource(strings = { "VI", "XIV", "XLIII", "DCCCXXXI", "MMCCCXXIII" })
    public void parseRoman_ThenParseArabic_ShouldReturnSameInstance(String roman) {
        parseAndAssert(roman);
    }

    @ParameterizedTest
    @MethodSource("data")
    public void parseRoman_DifferentRepresentationsOfSameArabic_ArabicShouldReturnSameInstance(String firstRoman, String secondRoman) {
        RomanInteger firstRomanParsed = RomanInteger.parse(firstRoman);
        RomanInteger secondRomanParsed = RomanInteger.parse(secondRoman);
        assertNotSame(firstRomanParsed, secondRomanParsed, "Instances of different representations should not be the same");
        String errorMessage = "Parsing the same arabic representation should have returned the result of the initial parsing";
        assertSame(firstRomanParsed, RomanInteger.parse(Integer.toString(firstRomanParsed.getArabic())), errorMessage);
        assertSame(firstRomanParsed, RomanInteger.parse(Integer.toString(secondRomanParsed.getArabic())), errorMessage);
    }

    private static List<Arguments> data() {
        return List.of(Arguments.of("IV", "IIII"), Arguments.of("XVIII", "IIXX"), Arguments.of("XLV", "VL"));
    }
}
