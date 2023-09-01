package com.github.chaosfirebolt.converter.api;

import com.github.chaosfirebolt.converter.RomanInteger;

/**
 * Map based roman integer cache
 */
public class MapRomanIntegerCache extends MapCache<String, RomanInteger> implements RomanIntegerCache {

    /**
     * Constructs an instance of Map based roman integer cache
     * @param parserCache parsers cache
     */
    public MapRomanIntegerCache(ParserCache parserCache) {
        super(new ParseComputation(parserCache), () -> new RuntimeException("Could not get a value"), new RomanIntegerMap());
    }
}
