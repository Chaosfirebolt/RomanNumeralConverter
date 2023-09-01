package com.github.chaosfirebolt.converter.parser.impl;

import com.github.chaosfirebolt.converter.util.ParsedData;

/**
 * Represents a parser.
 */
public sealed interface Parser permits ArabicParser, RomanParser {

    /**
     * Parses provided string to dto, containing roman numeral string, and it's corresponding integer in arabic format.
     *
     * @param input string for parsing.
     * @return object containing roman and arabic numeral.
     * @throws IllegalArgumentException if resulting, or provided arabic numeral, is not in the valid range for roman numerals.
     * @throws NumberFormatException if provided string does not match format required by the implementing class
     * @see ParsedData
     */
    ParsedData parse(String input);
}