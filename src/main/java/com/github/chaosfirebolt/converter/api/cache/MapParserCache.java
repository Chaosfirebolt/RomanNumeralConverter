package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.api.InvalidResultException;
import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.Parser;

import java.util.EnumMap;

/**
 * Cache for parsers, using a map
 */
public class MapParserCache extends MapCache<IntegerType, Parser> implements ParserCache {

    /**
     * Constructs new instance of Map based cache for parsers
     */
    public MapParserCache() {
        super(IntegerType::getParser, () -> new InvalidResultException("Returned value was null"), new EnumMap<>(IntegerType.class));
    }
}
