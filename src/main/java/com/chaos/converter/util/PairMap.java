package com.chaos.converter.util;

import com.chaos.converter.RomanInteger;

import java.util.*;

/**
 * Created by ChaosFire on 01-Mar-18
 */
public final class PairMap {

    private static PairMap instance;

    private Map<String, Integer> romanToArabic;
    private NavigableMap<Integer, String> arabicToRoman;

    private PairMap(Map<String, Integer> romanToArabic, NavigableMap<Integer, String> arabicToRoman) {
        this.romanToArabic = romanToArabic;
        this.arabicToRoman = arabicToRoman;
    }

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

    public Map<String, Integer> getRomanToArabic() {
        return this.romanToArabic;
    }

    public NavigableMap<Integer, String> getArabicToRoman() {
        return this.arabicToRoman;
    }
}