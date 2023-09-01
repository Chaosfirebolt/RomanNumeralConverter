package com.github.chaosfirebolt.converter.api;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.parser.impl.Parser;

import java.util.EnumMap;

/**
 * Cache for parsers, using a map
 */
public class MapParserCache extends MapCache<IntegerType, Parser> implements ParserCache {

    /**
     * Constructs new instance of Map based cache for parsers
     */
    public MapParserCache() {
        super(IntegerType::getParser, () -> new RuntimeException("Could not get a value"), new EnumMap<>(IntegerType.class));
    }
}
