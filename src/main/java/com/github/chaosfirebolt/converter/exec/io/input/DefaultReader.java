package com.github.chaosfirebolt.converter.exec.io.input;

import com.github.chaosfirebolt.converter.exec.io.Delimiter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * The default implementation of a {@link Reader}.
 */
public final class DefaultReader implements Reader {

  private final InputStream inputStream;
  private final Delimiter delimiter;
  private final int bufferSize;
  private final Charset charset;

  private DefaultReader(InputStream inputStream, Delimiter delimiter, int bufferSize, Charset charset) {
    this.inputStream = inputStream;
    this.delimiter = delimiter;
    this.bufferSize = bufferSize;
    this.charset = charset;
  }

  /**
   * Creates a new {@link Builder} instance.
   *
   * @return a new builder
   */
  public static Builder builder() {
    return new Builder();
  }

  @Override
  public Stream<String> read() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset), bufferSize);
    return reader
            .lines()
            .takeWhile(line -> !line.isEmpty())
            .map(delimiter)
            .flatMap(Arrays::stream);
  }

  /**
   * A builder for {@link DefaultReader}.
   */
  public static final class Builder {

    private InputStream inputStream;
    private Delimiter delimiter = Delimiter.empty();
    private int bufferSize = 2048;
    private Charset charset = StandardCharsets.UTF_8;

    private Builder() {
    }

    /**
     * Sets an input stream to read from.
     *
     * @param inputStream stream to read
     * @return this builder
     */
    public Builder setInputStream(InputStream inputStream) {
      this.inputStream = inputStream;
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
     * Sets the buffer size for reading. The default is 2048.
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
     * Sets the charset to use when reading. The default is {@link StandardCharsets#UTF_8}.
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
     * Creates a new {@link DefaultReader} instance.
     * Throws exception if no input stream to read has been set.
     *
     * @return new default reader instance
     * @throws NullPointerException if {@link #inputStream} is null
     */
    public DefaultReader build() {
      return new DefaultReader(Objects.requireNonNull(inputStream, "inputStream is null"), delimiter, bufferSize, charset);
    }
  }
}
