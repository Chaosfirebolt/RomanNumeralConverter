package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.InvalidResultException;
import com.github.chaosfirebolt.converter.api.cache.MapCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;
import com.github.chaosfirebolt.converter.api.initialization.BasicNumeralsInputSource;
import com.github.chaosfirebolt.converter.api.initialization.RomanIntegerArrayInitializationSource;

import java.util.HashMap;

/**
 * Map based roman integer cache
 */
//TODO make public to allow initialization through this path
class MapRomanIntegerCache extends MapCache<String, RomanInteger> implements RomanIntegerCache {

    /**
     * Constructs an instance of Map based roman integer cache
     * @param parserCache parsers cache
     */
    MapRomanIntegerCache(ParserCache parserCache) {
        super(new ParseComputation(parserCache), () -> new InvalidResultException("Returned value was null"), new HashMap<>(), new RomanIntegerArrayInitializationSource(new BasicNumeralsInputSource()));
    }
}
