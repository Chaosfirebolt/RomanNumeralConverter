package com.github.chaosfirebolt.converter.util;

import com.github.chaosfirebolt.converter.RomanInteger;

/**
 * Created by ChaosFire on 15-Mar-18
 *
 * Dto class, holds roman and it's corresponding arabic numeral, for the
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
    private final Integer arabic;

    public DataTransferObject(String roman, Integer arabic) {
        this.roman = roman;
        this.arabic = arabic;
    }

    public String getRoman() {
        return this.roman;
    }

    public Integer getArabic() {
        return this.arabic;
    }
}