package com.github.chaosfirebolt.converter.exec.io.output;

import com.github.chaosfirebolt.converter.exec.io.Delimiter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DefaultWriterTests {

  @Test
  public void hasDelimiter_shouldWriteCorrectly() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    DefaultWriter writer = DefaultWriter.builder()
            .setOutputStream(outputStream)
            .setDelimiter(Delimiter.of("-"))
            .setBufferSize(100)
            .setElementsPerLine(3)
            .build();
    String element = "a";
    try (writer) {
      for (int i = 0; i < 5; i++) {
        writer.write(element);
      }
    } catch (IOException exc) {
      throw new AssertionError(exc);
    }
    String actual = outputStream.toString(StandardCharsets.UTF_8);
    String expected = "a-a-a" + System.lineSeparator() + "a-a";
    assertEquals(expected, actual, "Writing is not correct");
  }

  @Test
  public void noDelimiter_shouldWriteCorrectly() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    DefaultWriter writer = DefaultWriter.builder()
            .setOutputStream(outputStream)
            .setBufferSize(100)
            .setElementsPerLine(3)
            .build();
    String element = "a";
    try (writer) {
      for (int i = 0; i < 5; i++) {
        writer.write(element);
      }
    } catch (IOException exc) {
      throw new AssertionError(exc);
    }
    String actual = outputStream.toString(StandardCharsets.UTF_8);
    String expected = "aaa" + System.lineSeparator() + "aa";
    assertEquals(expected, actual, "Writing is not correct");
  }
}
