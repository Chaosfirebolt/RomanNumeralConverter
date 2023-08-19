package com.github.chaosfirebolt.converter.constants;

import com.github.chaosfirebolt.converter.parser.impl.AbstractParser;
import com.github.chaosfirebolt.converter.parser.impl.ArabicParser;
import com.github.chaosfirebolt.converter.parser.impl.RomanParser;

import java.util.function.Supplier;

/**
 * Enumeration for supported integer types.
 */
public enum IntegerType {

    /**
     * Representation for arabic format integers.
     */
    ARABIC(ArabicParser::new),
    /**
     * Representation for roman format integers.
     */
    ROMAN(RomanParser::new);

    /**
     * Supplier for instances of parsers.
     */
    private final Supplier<AbstractParser> supplier;

    IntegerType(Supplier<AbstractParser> supplier) {
        this.supplier = supplier;
    }

    public AbstractParser getParser() {
        return this.supplier.get();
    }
}