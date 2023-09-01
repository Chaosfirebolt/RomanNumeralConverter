package com.github.chaosfirebolt.converter;

import java.util.HashMap;

/**
 * Maps String numerals to RomanIntegers.
 */
class RomanIntegerMap extends HashMap<String, RomanInteger> {

    /**
     * Creates a new instance and initializes it with the seven basic roman numerals.
     */
    RomanIntegerMap() {
        this.initialize(RomanInteger.ONE, RomanInteger.FIVE, RomanInteger.TEN, RomanInteger.FIFTY, RomanInteger.HUNDRED, RomanInteger.FIVE_HUNDRED, RomanInteger.THOUSAND);
    }

    private void initialize(RomanInteger... romanIntegers) {
        for (RomanInteger romanInteger : romanIntegers) {
            this.put(romanInteger.getRoman(), romanInteger);
            this.put(Integer.toString(romanInteger.getArabic()), romanInteger);
        }
    }
}
