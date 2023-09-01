package com.github.chaosfirebolt.converter.parser.impl;

import com.github.chaosfirebolt.converter.constants.Patterns;
import com.github.chaosfirebolt.converter.util.ParsedData;
import com.github.chaosfirebolt.converter.util.PairMap;
import com.github.chaosfirebolt.converter.util.Validator;

import java.util.Map;

/**
 * Class used to parse strings in roman numeral format to dto objects.
 * @see ParsedData
 * @see Parser
 */
public final class RomanParser implements Parser {

    @Override
    public ParsedData parse(String number) {
        number = Validator.numberFormat(number.trim().toUpperCase(), Patterns.ROMAN_PATTERN);
        Map<String, Integer> romanToArab = PairMap.getInstance().getRomanToArabic();
        int arabic = 0;
        boolean add = true;
        for (int i = number.length() - 1; i >= 0; i--) {
            int current = romanToArab.get(Character.toString(number.charAt(i)));
            if (i < number.length() - 1) {
                int previous = romanToArab.get(Character.toString(number.charAt(i + 1)));
                if (current > previous) {
                    add = true;
                } else if (current < previous){
                    add = false;
                }
            }
            arabic = add ? Validator.range(arabic + current) : Validator.range(arabic - current);
        }
        return new ParsedData(number, Validator.range(arabic));
    }
}