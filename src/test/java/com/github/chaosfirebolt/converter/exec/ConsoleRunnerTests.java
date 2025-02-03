package com.github.chaosfirebolt.converter.exec;

import com.github.chaosfirebolt.converter.exec.io.Delimiter;
import com.github.chaosfirebolt.converter.exec.io.input.DefaultReader;
import com.github.chaosfirebolt.converter.exec.io.input.ReaderFactory;
import com.github.chaosfirebolt.converter.exec.io.output.DefaultWriter;
import com.github.chaosfirebolt.converter.exec.io.output.WriterFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleRunnerTests {

  @Test
  public void writeShouldBeCorrect() {
    String input = "1;2;3" + System.lineSeparator() + "4;5";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));

    Delimiter delimiter = Delimiter.of(";");
    ReaderFactory readerFactory = () -> DefaultReader.builder().setDelimiter(delimiter).setInputStream(inputStream).build();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    WriterFactory writerFactory = () -> DefaultWriter.builder().setElementsPerLine(2).setDelimiter(delimiter).setOutputStream(outputStream).build();

    ConsoleRunner runner = new ConsoleRunner(readerFactory, writerFactory);
    runner.run();

    String actual = outputStream.toString(StandardCharsets.UTF_8);
    String expected = "1 - I;2 - II" + System.lineSeparator() + "3 - III;4 - IV" + System.lineSeparator() + "5 - V";
    assertEquals(expected, actual, "Wrong output");
  }
}
