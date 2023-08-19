package com.github.chaosfirebolt.converter.parser;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.parser.impl.AbstractParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/**
 * Created by Boyan Georgiev on 19-Aug-23
 */
@RunWith(Parameterized.class)
public class ParserContainerTests {

    private final IntegerType integerType;

    @Parameterized.Parameters(name = "Testing ParserContainer with {0}")
    public static Collection<IntegerType> data() {
        return Stream.concat(Stream.of(IntegerType.values()), Stream.of((IntegerType) null)).collect(Collectors.toList());
    }

    public ParserContainerTests(IntegerType integerType) {
        this.integerType = integerType;
    }

    @Test
    public void getParser_ShouldNotReturnNull() {
        assumeTrue("Test should run with non null Integer type", Objects.nonNull(this.integerType));
        AbstractParser parser = ParserContainer.getInstance().getParser(this.integerType);
        assertNotNull("Container should not have returned null", parser);
    }

    @Test
    public void getParserConsecutiveCalls_ShouldReturnSameInstance() {
        assumeTrue("Test should run with non null Integer type", Objects.nonNull(this.integerType));
        AbstractParser firstResult = ParserContainer.getInstance().getParser(this.integerType);
        AbstractParser secondResult = ParserContainer.getInstance().getParser(this.integerType);
        assertSame("Container should have returned same instance", firstResult, secondResult);
    }

    @Test
    public void getParserWithNull_ShouldThrowNpeWithMessage() {
        assumeTrue("Test should run with null IntegerType", Objects.isNull(this.integerType));
        NullPointerException npe = assertThrows("Should have thrown NPE", NullPointerException.class, () -> ParserContainer.getInstance().getParser(null));
        String message = npe.getMessage();
        assertTrue("Error message should have existed", message != null && !message.isEmpty());
    }
}
