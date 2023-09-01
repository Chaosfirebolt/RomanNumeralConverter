package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;

sealed abstract class BaseParser implements Parser permits ArabicParser, RomanParser {

    protected final IntegerType integerType;

    protected BaseParser(IntegerType integerType) {
        this.integerType = integerType;
    }
}
