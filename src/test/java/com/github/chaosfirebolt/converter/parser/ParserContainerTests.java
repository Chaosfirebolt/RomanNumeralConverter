package com.github.chaosfirebolt.converter.parser;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.parser.impl.AbstractParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

@RunWith(Parameterized.class)
public class ParserContainerTests {

    private final IntegerType integerType;

    @Parameterized.Parameters(name = "Testing ParserContainer with {0}")
    public static Collection<IntegerType> data() {
        return Arrays.asList(IntegerType.values());
    }

    public ParserContainerTests(IntegerType integerType) {
        this.integerType = integerType;
    }

    @Test
    public void getParser_ShouldNotReturnNull() {
        AbstractParser parser = ParserContainer.getInstance().getParser(this.integerType);
        assertNotNull("Container should not have returned null", parser);
    }

    @Test
    public void getParserConsecutiveCalls_ShouldReturnSameInstance() {
        AbstractParser firstResult = ParserContainer.getInstance().getParser(this.integerType);
        AbstractParser secondResult = ParserContainer.getInstance().getParser(this.integerType);
        assertSame("Container should have returned same instance", firstResult, secondResult);
    }
}
