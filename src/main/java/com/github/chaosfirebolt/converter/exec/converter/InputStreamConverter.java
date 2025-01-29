package com.github.chaosfirebolt.converter.exec.converter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Converts a command line argument to an {@link InputStream};
 */
public final class InputStreamConverter extends BaseStreamConverter<InputStream> {

  @Override
  InputStream createStream(IOData data) throws IOException {
    return switch (data.type()) {
      case CONSOLE -> new NonClosingInputStream(System.in);
      case FILE -> new FileInputStream(data.path());
    };
  }

  private static final class NonClosingInputStream extends InputStream {

    private final InputStream in;

    private NonClosingInputStream(InputStream in) {
      this.in = in;
    }

    @Override
    public int read() throws IOException {
      return in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
      return in.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
      return in.read(b, off, len);
    }

    @Override
    public byte[] readAllBytes() throws IOException {
      return in.readAllBytes();
    }

    @Override
    public byte[] readNBytes(int len) throws IOException {
      return in.readNBytes(len);
    }

    @Override
    public int readNBytes(byte[] b, int off, int len) throws IOException {
      return in.readNBytes(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
      return in.skip(n);
    }

    @Override
    public void skipNBytes(long n) throws IOException {
      in.skipNBytes(n);
    }

    @Override
    public int available() throws IOException {
      return in.available();
    }

    @Override
    public void close() {
      //don't close
    }

    @Override
    public void mark(int readlimit) {
      in.mark(readlimit);
    }

    @Override
    public void reset() throws IOException {
      in.reset();
    }

    @Override
    public boolean markSupported() {
      return in.markSupported();
    }

    @Override
    public long transferTo(OutputStream out) throws IOException {
      return in.transferTo(out);
    }
  }
}
