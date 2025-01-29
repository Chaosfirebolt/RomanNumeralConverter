package com.github.chaosfirebolt.converter.exec;

import com.beust.jcommander.Parameter;
import com.github.chaosfirebolt.converter.exec.converter.DelimiterConverter;
import com.github.chaosfirebolt.converter.exec.converter.InputStreamConverter;
import com.github.chaosfirebolt.converter.exec.converter.OutputStreamConverter;
import com.github.chaosfirebolt.converter.exec.io.Delimiter;
import com.github.chaosfirebolt.converter.exec.io.input.DefaultReader;
import com.github.chaosfirebolt.converter.exec.io.input.Reader;
import com.github.chaosfirebolt.converter.exec.io.input.ReaderFactory;
import com.github.chaosfirebolt.converter.exec.io.output.DefaultWriter;
import com.github.chaosfirebolt.converter.exec.io.output.Writer;
import com.github.chaosfirebolt.converter.exec.io.output.WriterFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
class Configuration implements ReaderFactory, WriterFactory {

  private static final Charset CHARSET = StandardCharsets.UTF_8;

  private InputStream inputStream;
  private Delimiter inputDelimiter;
  private int ioBufferSize;

  private OutputStream outputStream;
  private Delimiter outputDelimiter;
  private int elementsPerLine;

  @Parameter(names = {"--input", "-i"}, description = "Input to read from", converter = InputStreamConverter.class)
  void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  @Parameter(names = {"--input-delimiter", "-id"}, description = "Delimiter for reading", converter = DelimiterConverter.class)
  void setInputDelimiter(Delimiter inputDelimiter) {
    this.inputDelimiter = inputDelimiter;
  }

  @Parameter(names = {"--buffer", "-b"}, description = "Buffer size for reading/writing")
  void setIoBufferSize(int ioBufferSize) {
    this.ioBufferSize = ioBufferSize;
  }

  @Parameter(names = {"--output", "-o"}, description = "Output to write to", converter = OutputStreamConverter.class)
  void setOutputStream(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  @Parameter(names = {"--output-delimiter", "-od"}, description = "Delimiter for writing", converter = DelimiterConverter.class)
  void setOutputDelimiter(Delimiter outputDelimiter) {
    this.outputDelimiter = outputDelimiter;
  }

  @Parameter(names = {"--elements", "-e"}, description = "Number of elements to write on a single line")
  void setElementsPerLine(int elementsPerLine) {
    this.elementsPerLine = elementsPerLine;
  }

  @Override
  public Reader createReader() {
    return DefaultReader.builder()
            .setInputStream(inputStream)
            .setDelimiter(inputDelimiter)
            .setBufferSize(ioBufferSize)
            .setCharset(CHARSET)
            .build();
  }

  @Override
  public Writer createWriter() {
    return DefaultWriter.builder()
            .setOutputStream(outputStream)
            .setDelimiter(outputDelimiter)
            .setBufferSize(ioBufferSize)
            .setCharset(CHARSET)
            .setElementsPerLine(elementsPerLine)
            .build();
  }
}
