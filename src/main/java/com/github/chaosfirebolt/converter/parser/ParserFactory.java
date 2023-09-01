package com.github.chaosfirebolt.converter.parser;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.parser.impl.Parser;

import java.util.Objects;

/**
 * Factory class, providing instances of {@link Parser}.
 */
public class ParserFactory {

    private ParserFactory() {
        //no instances allowed
    }

    /**
     * Creates instances of {@link Parser}.
     *
     * @param integerType type of integer, whose parser is needed.
     * @return instance of {@link Parser} for this type of {@link IntegerType}
     */
    public static Parser createParser(IntegerType integerType) {
        Objects.requireNonNull(integerType, "Integer type can't be null");
        return integerType.getParser();
    }
}