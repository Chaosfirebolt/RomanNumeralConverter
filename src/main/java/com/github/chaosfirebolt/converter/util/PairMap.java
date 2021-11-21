package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.RomanInteger;

import java.util.*;

/**
 * Created by ChaosFire on 01-Mar-18
 *
 * Singleton holding mappings from arabic to roman, and roman to arabic basic numerals.
 */
public final class PairMap {

    /**
     * Instance of the class.
     */
    private static PairMap instance;

    /**
     * Roman to arabic unmodifiable mapping.
     */
    private final Map<String, Integer> romanToArabic;
    /**
     * Arabic to roman unmodifiable mapping.
     */
    private final NavigableMap<Integer, String> arabicToRoman;

    private PairMap(Map<String, Integer> romanToArabic, NavigableMap<Integer, String> arabicToRoman) {
        this.romanToArabic = romanToArabic;
        this.arabicToRoman = arabicToRoman;
    }

    /**
     * Getter for instance.
     *
     * @return single instance of the class.
     */
    public static PairMap getInstance() {
        if (instance == null) {
            Map<String, Integer> toArab = new HashMap<>();
            NavigableMap<Integer, String> toRoman = new TreeMap<>();

            add(toArab, toRoman, RomanInteger.ONE);
            add(toArab, toRoman, RomanInteger.FIVE);
            add(toArab, toRoman, RomanInteger.TEN);
            add(toArab, toRoman, RomanInteger.FIFTY);
            add(toArab, toRoman, RomanInteger.HUNDRED);
            add(toArab, toRoman, RomanInteger.FIVE_HUNDRED);
            add(toArab, toRoman, RomanInteger.THOUSAND);
            instance = new PairMap(Collections.unmodifiableMap(toArab), Collections.unmodifiableNavigableMap(toRoman));
        }
        return instance;
    }

    private static void add(Map<String, Integer> toArab, NavigableMap<Integer, String> toRoman, RomanInteger romanInteger) {
        toArab.put(romanInteger.toString(), romanInteger.getArabic());
        toRoman.put(romanInteger.getArabic(), romanInteger.toString());
    }

    /**
     * Getter method for roman to arabic mapping.
     *
     * @return unmodifiable mapping.
     */
    public Map<String, Integer> getRomanToArabic() {
        return this.romanToArabic;
    }

    /**
     * Getter method for arabic to roman mapping.
     *
     * @return unmodifiable mapping.
     */
    public NavigableMap<Integer, String> getArabicToRoman() {
        return this.arabicToRoman;
    }
}