package com.github.chaosfirebolt.converter.api;

import com.github.chaosfirebolt.converter.RomanInteger;

/**
 * Non caching implementation for {@link RomanInteger} cache.
 */
public class NoOpRomanIntegerCache extends NoOpCache<String, RomanInteger> implements RomanIntegerCache {

    /**
     * Constructs an instance of non caching roman integer cache
     * @param parserCache parsers cache
     */
    public NoOpRomanIntegerCache(ParserCache parserCache) {
        super(new ParseComputation(parserCache), () -> new RuntimeException("Could not get a value"));
    }
}
