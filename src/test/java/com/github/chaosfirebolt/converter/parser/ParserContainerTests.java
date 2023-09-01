package com.github.chaosfirebolt.converter.parser;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.parser.impl.Parser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ParserContainerTests {

    @ParameterizedTest
    @EnumSource
    public void getParser_ShouldNotReturnNull(IntegerType integerType) {
        Parser parser = ParserContainer.getInstance().getParser(integerType);
        assertNotNull(parser, "Container should not have returned null");
    }

    @ParameterizedTest
    @EnumSource
    public void getParserConsecutiveCalls_ShouldReturnSameInstance(IntegerType integerType) {
        Parser firstResult = ParserContainer.getInstance().getParser(integerType);
        Parser secondResult = ParserContainer.getInstance().getParser(integerType);
        assertSame(firstResult, secondResult, "Container should have returned same instance");
    }
}
