package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.MapCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;

/**
 * Map based roman integer cache
 */
class MapRomanIntegerCache extends MapCache<String, RomanInteger> implements RomanIntegerCache {

    /**
     * Constructs an instance of Map based roman integer cache
     * @param parserCache parsers cache
     */
    MapRomanIntegerCache(ParserCache parserCache) {
        super(new ParseComputation(parserCache), () -> new RuntimeException("Could not get a value"), new RomanIntegerMap());
    }
}
