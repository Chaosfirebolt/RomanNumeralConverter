package com.chaos.converter.parser.impl;

import com.chaos.converter.constants.Patterns;
import com.chaos.converter.util.DataTransferObject;
import com.chaos.converter.util.PairMap;
import com.chaos.converter.util.Validator;

import java.util.Map;

/**
 * Created by ChaosFire on 01-Mar-18
 *
 * Class used to parse strings in roman numeral format to dto objects.
 * @see DataTransferObject
 * @see AbstractParser
 */
class RomanParser extends AbstractParser {

    RomanParser() {
        super();
    }

    @Override
    public DataTransferObject parse(String number) {
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

            if (add) {
                arabic += current;
            } else {
                arabic -= current;
            }
        }
        return new DataTransferObject(number, Validator.range(arabic));
    }
}