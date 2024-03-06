package com.github.chaosfirebolt.converter.api.initialization.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

/**
 * Source returning {@link InputStream}.
 *
 * @param <StreamResult> type of the data read from the stream
 * @since 3.2.0
 */
public abstract class InputStreamSource<StreamResult> implements InputSource<StreamResult> {

  private final String path;

  /**
   * @param path path to be used for creating the stream
   * @since 3.2.0
   */
  protected InputStreamSource(String path) {
    this.path = path;
  }

  /**
   * Returns a result after reading from an {@link InputStream}.
   * Wraps and rethrows {@link IOException} as an {@link UncheckedIOException}.
   *
   * @return result from reading an input stream
   * @throws UncheckedIOException in case of {@link IOException}
   */
  @Override
  public final StreamResult getInputData() {
    try (InputStream inputStream = this.createInputStream(this.path)) {
      return this.readInputStream(inputStream);
    } catch (IOException exc) {
      throw new UncheckedIOException(exc);
    }
  }

  /**
   * Creates an input stream from provided path.
   *
   * @param path path to be used for creating the stream
   * @return a new {@link InputStream}
   * @throws IOException if creating the stream fails for whatever reason
   * @since 3.2.0
   */
  protected abstract InputStream createInputStream(String path) throws IOException;

  /**
   * Reads the input stream.
   *
   * @param inputStream stream to read from
   * @return data from the stream
   * @throws IOException if reading from the stream fails
   * @since 3.2.0
   */
  protected abstract StreamResult readInputStream(InputStream inputStream) throws IOException;
}
