package com.github.chaosfirebolt.converter.exec.io;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DelimiterTests {

  @Test
  public void emptyDelimiter_ShouldSplitIntoArrayOfInput() {
    Delimiter delimiter = Delimiter.empty();
    String input = "abc";
    String[] result = delimiter.split(input);
    assertEquals(1, result.length, "Should have one element");
    assertEquals(input, result[0], "Incorrect element value");
  }

  @Test
  public void notEmptyDelimiter_ShouldSplitCorrectly() {
    Delimiter delimiter = Delimiter.of("\\s");
    String input = "abc\\sas d";
    String[] result = delimiter.split(input);
    assertArrayEquals(new String[]{"abc", "as d"}, result, "Incorrect split array");
  }

  @Test
  public void delimiterApply_ShouldInvokeSplit() {
    Delimiter d = Delimiter.of("b");
    Delimiter delimiter = spy(d);
    String input = "abc";
    delimiter.apply(input);
    verify(delimiter, times(1)).split(input);
  }
}
