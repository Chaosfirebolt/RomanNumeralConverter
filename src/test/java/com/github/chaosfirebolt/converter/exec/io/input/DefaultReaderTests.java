package com.github.chaosfirebolt.converter.exec.io.input;

import com.github.chaosfirebolt.converter.exec.io.Delimiter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultReaderTests {

  private static final String ERROR_MESSAGE = "Incorrect reading";

  @Test
  public void emptyDelimiter_ShouldReadCorrectly() {
    String input = """
            qwe
            rty
            asd
            """;
    DefaultReader reader = DefaultReader.builder()
            .setInputStream(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)))
            .setBufferSize(100)
            .build();
    List<String> result = reader.read().toList();
    List<String> expected = List.of("qwe", "rty", "asd");
    assertEquals(expected, result, ERROR_MESSAGE);
  }

  @Test
  public void nonEmptyDelimiter_ShouldReadCorrectly() {
    String input = """
            qw-e
            r-ty
            a-s-d
            """;
    DefaultReader reader = DefaultReader.builder()
            .setInputStream(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)))
            .setDelimiter(Delimiter.of("-"))
            .setBufferSize(100)
            .build();
    List<String> result = reader.read().toList();
    List<String> expected = List.of("qw", "e", "r", "ty", "a", "s", "d");
    assertEquals(expected, result, ERROR_MESSAGE);
  }
}
