package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.util.PairMap;

import java.util.Map;
import java.util.NavigableMap;

/**
 * Class used to parse strings in arabic numeral format to {@link RomanInteger}.
 * @see Parser
 */
public final class ArabicParser extends BaseParser {

    /**
     * Constructs a new instance, providing the required {@link IntegerType}
     */
    public ArabicParser() {
        super(IntegerType.ARABIC);
    }

    @Override
    public RomanInteger parse(String number) {
        number = integerType.validateFormat(number.trim());
        int arabicValue = integerType.validateRange(Integer.parseInt(number));
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
        return new RomanInteger(roman.toString(), arabicValue);
    }
}