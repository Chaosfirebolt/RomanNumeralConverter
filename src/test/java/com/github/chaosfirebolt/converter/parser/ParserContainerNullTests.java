package com.github.chaosfirebolt.converter.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserContainerNullTests {

    @Test
    public void getParserWithNull_ShouldThrowNpeWithMessage() {
        NullPointerException npe = assertThrows(NullPointerException.class, () -> ParserContainer.getInstance().getParser(null), "Should have thrown NPE");
        String message = npe.getMessage();
        assertTrue(message != null && !message.isEmpty(), "Error message should have existed");
    }
}
