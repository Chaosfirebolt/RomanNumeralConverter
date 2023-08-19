package com.github.chaosfirebolt.converter.parser;

import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ParserContainerNullTests {

    @Test
    public void getParserWithNull_ShouldThrowNpeWithMessage() {
        NullPointerException npe = assertThrows("Should have thrown NPE", NullPointerException.class, () -> ParserContainer.getInstance().getParser(null));
        String message = npe.getMessage();
        assertTrue("Error message should have existed", message != null && !message.isEmpty());
    }
}
