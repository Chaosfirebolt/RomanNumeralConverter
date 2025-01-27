package com.github.chaosfirebolt.converter.exec.io.output;

import com.github.chaosfirebolt.converter.exec.io.Delimiter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * The default implementation of {@link Writer}.
 */
public final class DefaultWriter implements Writer {

  private final BufferedWriter writer;
  private final Delimiter delimiter;
  private final int elementsPerLine;
  private int writtenPerLine;

  private DefaultWriter(BufferedWriter writer, Delimiter delimiter, int elementsPerLine) {
    this.writer = writer;
    this.delimiter = delimiter;
    this.elementsPerLine = elementsPerLine;
    this.writtenPerLine = 0;
  }

  /**
   * Creates a new builder for {@link DefaultWriter}.
   *
   * @return new builder
   */
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public void write(String value) throws IOException {
    if (writtenPerLine >= elementsPerLine) {
      writer.write(System.lineSeparator());
      writtenPerLine = 0;
    }
    if (writtenPerLine >= 1 && !delimiter.isEmpty()) {
      writer.write(delimiter.value());
    }
    writer.write(value);
    writtenPerLine++;
  }

  @Override
  public void close() throws IOException {
    writer.close();
  }

  /**
   * A builder for {@link DefaultWriter}.
   */
  public static final class Builder {

    private OutputStream outputStream;
    private Delimiter delimiter = Delimiter.empty();
    private int bufferSize = 2048;
    private Charset charset = StandardCharsets.UTF_8;
    private int elementsPerLine = 10;

    private Builder() {
    }

    /**
     * Sets an output stream to write to.
     *
     * @param outputStream stream to write to
     * @return this builder
     */
    public Builder setOutputStream(OutputStream outputStream) {
      this.outputStream = outputStream;
      return this;
    }

    /**
     * Sets the delimiter to use. The default delimiter is empty.
     * Does not set null.
     *
     * @param delimiter delimiter to use
     * @return this builder
     */
    public Builder setDelimiter(Delimiter delimiter) {
      if (delimiter != null) {
        this.delimiter = delimiter;
      }
      return this;
    }

    /**
     * Sets the buffer size for writing. The default is 2048.
     * Does not set values less than one.
     *
     * @param bufferSize size of the buffer
     * @return this builder
     */
    public Builder setBufferSize(int bufferSize) {
      if (bufferSize > 0) {
        this.bufferSize = bufferSize;
      }
      return this;
    }

    /**
     * Sets the charset to use when writing. The default is {@link StandardCharsets#UTF_8}.
     * Does not set null.
     *
     * @param charset charset to use
     * @return this builder
     */
    public Builder setCharset(Charset charset) {
      if (charset != null) {
        this.charset = charset;
      }
      return this;
    }

    /**
     * Sets the number of elements to write on each line.
     * The default is ten.
     * Does not set a value less than one.
     *
     * @param elementsPerLine number of elements per line
     * @return this builder
     */
    public Builder setElementsPerLine(int elementsPerLine) {
      if (elementsPerLine > 0) {
        this.elementsPerLine = elementsPerLine;
      }
      return this;
    }

    /**
     * Creates a new {@link DefaultWriter} instance.
     * Throws an exception if no output stream to write to has been set.
     *
     * @return new default writer
     * @throws NullPointerException if output stream is null
     */
    public DefaultWriter build() {
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Objects.requireNonNull(outputStream, "output stream is null"), charset), bufferSize);
      return new DefaultWriter(writer, delimiter, elementsPerLine);
    }
  }
}
