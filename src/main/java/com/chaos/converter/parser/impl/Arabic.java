package com.chaos.converter.parser.impl;

import com.chaos.converter.RomanInteger;
import com.chaos.converter.constants.IntegerType;
import com.chaos.converter.exc.ConstructionException;
import com.chaos.converter.util.PairMap;
import com.chaos.converter.util.Validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.NavigableMap;

/**
 * Created by ChaosFire on 01-Mar-18
 */
class Arabic extends AbstractParser {

    @Override
    public RomanInteger parse(String number) {
        Validator.validateInput(number);
        Validator.validateArabic(number, IntegerType.ARABIC);
        StringBuilder roman = new StringBuilder();
        int num = Integer.parseInt(number);
        NavigableMap<Integer, String> arabicToRoman = PairMap.getInstance().getArabicToRoman();
        for (Map.Entry<Integer, String> entry : arabicToRoman.entrySet()) {
            int repeat = num / entry.getKey();
            if (repeat == 0) {
                continue;
            }
            int firstDigit = Integer.parseInt(entry.getKey().toString().substring(0, 1));
            if (firstDigit % 5 == 4) {
                Map.Entry<Integer, String> higher = arabicToRoman.higherEntry(num);
                int multiplier = higher.getKey() % 10 == 0 ? higher.getKey() / 10 : higher.getKey() / 5;
                String subtract = arabicToRoman.get(multiplier);
                roman.append(subtract).append(higher.getValue());
                num %= firstDigit * multiplier;
            } else {
                for (int i = 0; i < repeat; i++) {
                    roman.append(entry.getValue());
                }
                num %= entry.getKey();
            }
            if (num == 0) {
                break;
            }
        }
        try {
            ROMAN_INTEGER_CONSTRUCTOR.setAccessible(true);
            return ROMAN_INTEGER_CONSTRUCTOR.newInstance(roman.toString(), Integer.parseInt(number));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exc) {
            throw new ConstructionException("Can't instantiate RomanInteger", exc);
        }
    }
}