package com.chaos.converter.util;

import com.chaos.converter.RomanInteger;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by ChaosFire on 01-Mar-18
 */
public final class PairMap {

    private static PairMap instance;

    private NavigableMap<String, Integer> romanToArabic;
    private NavigableMap<Integer, String> arabicToRoman;

    private PairMap(NavigableMap<String, Integer> romanToArabic, NavigableMap<Integer, String> arabicToRoman) {
        this.romanToArabic = romanToArabic;
        this.arabicToRoman = arabicToRoman;
    }

    public static PairMap getInstance() {
        if (instance == null) {
            NavigableMap<String, Integer> toArab = new TreeMap<>();
            NavigableMap<Integer, String> toRoman = new TreeMap<>(Comparator.reverseOrder());
            final String excMsg = "Something went wrong when accessing values of public constants.";
            Arrays.stream(RomanInteger.class.getFields())
                    .filter(field -> field.getType().equals(RomanInteger.class) && Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))
                    .forEach(field -> {
                        RomanInteger romanInteger;
                        try {
                            romanInteger = (RomanInteger) field.get(null);
                        } catch (IllegalAccessException exc) {
                            throw new RuntimeException(excMsg, exc);
                        }
                        int arabicValue = Integer.parseInt(romanInteger.toStringArabic());
                        toArab.put(romanInteger.toString(), arabicValue);
                        toRoman.put(arabicValue, romanInteger.toString());
                    });
            instance = new PairMap(Collections.unmodifiableNavigableMap(toArab), Collections.unmodifiableNavigableMap(toRoman));
        }
        return instance;
    }

    public NavigableMap<String, Integer> getRomanToArabic() {
        return this.romanToArabic;
    }

    public NavigableMap<Integer, String> getArabicToRoman() {
        return this.arabicToRoman;
    }
}