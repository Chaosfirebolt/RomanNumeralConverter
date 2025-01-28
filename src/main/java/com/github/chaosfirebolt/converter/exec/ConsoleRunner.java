package com.github.chaosfirebolt.converter.exec;

import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.exec.io.input.Reader;
import com.github.chaosfirebolt.converter.exec.io.input.ReaderFactory;
import com.github.chaosfirebolt.converter.exec.io.output.Writer;
import com.github.chaosfirebolt.converter.exec.io.output.WriterFactory;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.stream.Stream;

class ConsoleRunner implements Runnable {

  private final ReaderFactory readerFactory;
  private final WriterFactory writerFactory;

  ConsoleRunner(ReaderFactory readerFactory, WriterFactory writerFactory) {
    this.readerFactory = readerFactory;
    this.writerFactory = writerFactory;
  }

  @Override
  public void run() {
    Reader reader = readerFactory.createReader();
    try (Stream<String> values = reader.read();
         Writer writer = writerFactory.createWriter()) {
      //TODO handle parse failures
      //TODO handle parse result
      values
              .map(RomanInteger::parse)
              .map(RomanInteger::toString)
              .forEach(new WritingConsumer(writer));
    } catch (IOException exc) {
      throw new UnrecoverableException(exc);
    }
  }

  private record WritingConsumer(Writer writer) implements Consumer<String> {

    @Override
      public void accept(String s) {
        try {
          writer.write(s);
        } catch (IOException exc) {
          throw new UnrecoverableException(exc);
        }
      }
    }
}
