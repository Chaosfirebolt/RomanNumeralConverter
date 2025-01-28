package com.github.chaosfirebolt.converter.exec;

import com.github.chaosfirebolt.converter.RomanInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ElementParserTests {

  private final Function<String, RomanInteger> romanIntegerParser = mock();
  private final Function<RomanInteger, String> successHandler = mock();
  private final Function<Exception, String> errorHandler = mock();
  private final ElementParser elementParser = new ElementParser(romanIntegerParser, successHandler, errorHandler);

  @BeforeEach
  public void setUp() {
    when(successHandler.apply(any())).thenAnswer(invocation -> invocation.getArgument(0).toString());
    when(errorHandler.apply(any())).thenAnswer(invocation -> {
      Exception error = invocation.getArgument(0);
      return error.getMessage();
    });
  }

  @Test
  public void successfulParse() {
    RomanInteger parseResult = RomanInteger.parse("111");
    when(romanIntegerParser.apply(any())).thenReturn(parseResult);

    String result = elementParser.apply("");
    assertEquals(parseResult.toString(), result, "Incorrect element parse result on success");
    verify(romanIntegerParser, times(1)).apply("");
    verify(successHandler, times(1)).apply(parseResult);
    verify(errorHandler, never()).apply(any());
  }

  @Test
  public void failedParse() {
    String errorMessage = "bla";
    RuntimeException exception = new RuntimeException(errorMessage);
    when(romanIntegerParser.apply(any())).thenThrow(exception);

    String result = elementParser.apply("11");
    assertEquals(errorMessage, result, "Incorrect element parse result on error");
    verify(romanIntegerParser, times(1)).apply("11");
    verify(successHandler, never()).apply(any());
    verify(errorHandler, times(1)).apply(exception);
  }
}
