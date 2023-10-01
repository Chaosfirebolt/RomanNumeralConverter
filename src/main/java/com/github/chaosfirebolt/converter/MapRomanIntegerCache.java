package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.InvalidResultException;
import com.github.chaosfirebolt.converter.api.cache.MapCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;
import com.github.chaosfirebolt.converter.api.initialization.BasicNumeralsInputSource;
import com.github.chaosfirebolt.converter.api.initialization.InitializationSource;
import com.github.chaosfirebolt.converter.api.initialization.RomanIntegerArrayInitializationSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Map based roman integer cache
 */
public class MapRomanIntegerCache extends MapCache<String, RomanInteger> implements RomanIntegerCache {

    /**
     * Constructs an instance of Map based roman integer cache
     * @param parserCache parsers cache
     */
    public MapRomanIntegerCache(ParserCache parserCache) {
        this(parserCache, new HashMap<>(), new RomanIntegerArrayInitializationSource(new BasicNumeralsInputSource()));
    }

    /**
     * Constructs an instance of Map based roman integer cache
     * @param parserCache parsers cache
     * @param cache map to cache roman integers in
     * @param initializationSource source to initialize the cache with
     */
    public MapRomanIntegerCache(ParserCache parserCache, Map<String, RomanInteger> cache, InitializationSource<Map<String, RomanInteger>> initializationSource) {
        super(new ParseComputation(parserCache), () -> new InvalidResultException("Returned value was null"), cache, initializationSource);
    }
}
