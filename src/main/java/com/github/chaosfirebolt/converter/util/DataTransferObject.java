package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.RomanInteger;

/**
 * Dto class, holds roman, and it's corresponding arabic numeral, for the
 * purpose of transferring parsed data to private constructors in {@link RomanInteger}.
 */
public class DataTransferObject {

    /**
     * Roman representation of a number.
     */
    private final String roman;
    /**
     * Arabic representation for same number.
     */
    private final int arabic;

    public DataTransferObject(String roman, int arabic) {
        this.roman = roman;
        this.arabic = arabic;
    }

    public String getRoman() {
        return this.roman;
    }

    public int getArabic() {
        return this.arabic;
    }
}