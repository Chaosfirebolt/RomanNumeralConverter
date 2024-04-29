package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.Parser;
import com.github.chaosfirebolt.converter.constants.IntegerType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public abstract class ParserCacheTests {

  private final ParserCache parserCache;

  protected ParserCacheTests(ParserCache parserCache) {
    this.parserCache = parserCache;
  }

  @ParameterizedTest
  @EnumSource
  public void getParser_ShouldNotReturnNull(IntegerType integerType) {
    Parser parser = parserCache.getValue(integerType);
    assertNotNull(parser, "Cache should not have returned null");
  }

  @ParameterizedTest
  @EnumSource
  public void getParserConsecutiveCalls_ShouldReturnSameInstance(IntegerType integerType) {
    Parser firstResult = parserCache.getValue(integerType);
    Parser secondResult = parserCache.getValue(integerType);
    assertSame(firstResult, secondResult, "Cache should have returned same instance");
  }

  @Test
  public void getParserWithNull_ShouldThrowNpeWithMessage() {
    NullPointerException npe = assertThrows(NullPointerException.class, () -> parserCache.getValue(null), "Should have thrown NPE");
    String message = npe.getMessage();
    assertTrue(message != null && !message.isEmpty(), "Error message should have existed");
  }
}
