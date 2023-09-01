package com.github.chaosfirebolt.converter.parser;

import com.github.chaosfirebolt.converter.api.cache.MapParserCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class ParserCacheTests {

    private final ParserCache parserCache = new MapParserCache();

    @ParameterizedTest
    @EnumSource
    public void getParser_ShouldNotReturnNull(IntegerType integerType) {
        Parser parser = this.parserCache.getValue(integerType);
        assertNotNull(parser, "Cache should not have returned null");
    }

    @ParameterizedTest
    @EnumSource
    public void getParserConsecutiveCalls_ShouldReturnSameInstance(IntegerType integerType) {
        Parser firstResult = this.parserCache.getValue(integerType);
        Parser secondResult = this.parserCache.getValue(integerType);
        assertSame(firstResult, secondResult, "Cache should have returned same instance");
    }

    @Test
    public void getParserWithNull_ShouldThrowNpeWithMessage() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> this.parserCache.getValue(null), "Should have thrown NPE");
        String message = npe.getMessage();
        assertTrue(message != null && !message.isEmpty(), "Error message should have existed");
    }
}
