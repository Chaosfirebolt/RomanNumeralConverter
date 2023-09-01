package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;

sealed abstract class BaseParser implements Parser permits ArabicParser, RomanParser {

    /**
     * Integer type for this parser
     */
    protected final IntegerType integerType;

    protected BaseParser(IntegerType integerType) {
        this.integerType = integerType;
    }
}
