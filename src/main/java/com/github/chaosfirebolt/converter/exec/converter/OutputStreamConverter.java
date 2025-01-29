package com.github.chaosfirebolt.converter.exec.converter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Converts command line argument to an {@link OutputStream}.
 */
public final class OutputStreamConverter extends BaseStreamConverter<OutputStream> {

  @Override
  OutputStream createStream(IOData data) throws IOException {
    return switch (data.type()) {
      case CONSOLE -> new NonClosingOutputStream(System.out);
      case FILE -> new FileOutputStream(data.path());
    };
  }

  private static final class NonClosingOutputStream extends OutputStream {

    private final OutputStream out;

    private NonClosingOutputStream(OutputStream out) {
      this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
      out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
      out.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      out.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
      out.flush();
    }

    @Override
    public void close() {
      //don't close
    }
  }
}
