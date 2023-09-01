package com.github.chaosfirebolt.converter.parser.impl;

import com.github.chaosfirebolt.converter.constants.Patterns;
import com.github.chaosfirebolt.converter.util.ParsedData;
import com.github.chaosfirebolt.converter.util.PairMap;
import com.github.chaosfirebolt.converter.util.Validator;

import java.util.Map;
import java.util.NavigableMap;

/**
 * Class used to parse strings in arabic numeral format to dto objects.
 * @see ParsedData
 * @see Parser
 */
public final class ArabicParser implements Parser {

    @Override
    public ParsedData parse(String number) {
        number = number.trim();
        int arabicValue = Validator.range(Integer.parseInt(Validator.numberFormat(number, Patterns.ARABIC_PATTERN)));
        StringBuilder roman = new StringBuilder();
        NavigableMap<Integer, String> arabToRoman = PairMap.getInstance().getArabicToRoman();
        for (int i = 0; i < number.length(); i++) {
            int digit = Integer.parseInt(Character.toString(number.charAt(i)));
            int power = number.length() - 1 - i;
            int exp = (int) Math.pow(10, power);

            int key = digit * exp;
            if (digit == 4 || digit == 9) {
                roman.append(arabToRoman.get(exp)).append(arabToRoman.higherEntry(key).getValue());
            } else {
                while (key > 0) {
                    Map.Entry<Integer, String> floorEntry = arabToRoman.floorEntry(key);
                    int repeat = key / floorEntry.getKey();
                    for (int j = 0; j < repeat; j++) {
                        roman.append(floorEntry.getValue());
                        key -= floorEntry.getKey();
                    }
                }
            }
        }
        return new ParsedData(roman.toString(), arabicValue);
    }
}