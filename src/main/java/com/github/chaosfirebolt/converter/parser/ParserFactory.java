package com.github.chaosfirebolt.converter.parser;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.parser.impl.AbstractParser;

import java.util.Objects;

/**
 * Created by ChaosFire on 01-Mar-18
 * <br/>
 * Factory class, providing instances of {@link AbstractParser}.
 */
public class ParserFactory {

    private ParserFactory() {
        //no instances allowed
    }

    /**
     * Creates instances of {@link AbstractParser}.
     *
     * @param integerType type of integer, whose parser is needed.
     * @return instance of {@link AbstractParser} for this type of {@link IntegerType}
     */
    public static AbstractParser createParser(IntegerType integerType) {
        Objects.requireNonNull(integerType, "Integer type can't be null");
        return integerType.getParser();
    }
}