package com.chaos.converter.parser.impl;

import com.chaos.converter.util.DataTransferObject;

/**
 * Created by ChaosFire on 01-Mar-18
 *
 * Parsers must extend this class.
 */
public abstract class AbstractParser {

    AbstractParser() {
    }

    /**
     * Parses provided string to dto, containing roman numeral string and it's corresponding integer in arabic format.
     *
     * @param number string for parsing.
     * @return object containing roman and arabic numeral.
     * @throws IllegalArgumentException if resulting, or provided arabic numeral, is not in the valid range for roman numerals.
     * @throws NumberFormatException if provided string does not match format required by the implementing class
     * @see DataTransferObject
     */
    public abstract DataTransferObject parse(String number);
}