package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.NoOpCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;

/**
 * Non caching implementation for {@link RomanInteger} cache.
 */
class NoOpRomanIntegerCache extends NoOpCache<String, RomanInteger> implements RomanIntegerCache {

    /**
     * Constructs an instance of non caching roman integer cache
     * @param parserCache parsers cache
     */
    NoOpRomanIntegerCache(ParserCache parserCache) {
        super(new ParseComputation(parserCache), () -> new RuntimeException("Could not get a value"));
    }
}
