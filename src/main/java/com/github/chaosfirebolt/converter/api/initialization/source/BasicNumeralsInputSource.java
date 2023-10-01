package com.github.chaosfirebolt.converter.api.initialization.source;

import com.github.chaosfirebolt.converter.RomanInteger;

/**
 * Source consisting of the 7 basic roman numerals.
 */
public class BasicNumeralsInputSource implements InputSource<RomanInteger[]> {

    @Override
    public RomanInteger[] getInputData() {
        return new RomanInteger[]{ RomanInteger.ONE, RomanInteger.FIVE, RomanInteger.TEN, RomanInteger.FIFTY, RomanInteger.HUNDRED, RomanInteger.FIVE_HUNDRED, RomanInteger.THOUSAND };
    }
}
