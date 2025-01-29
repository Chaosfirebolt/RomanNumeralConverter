package com.github.chaosfirebolt.converter.exec.converter;

import com.beust.jcommander.IStringConverter;

import java.io.IOException;

abstract sealed class BaseStreamConverter<T> implements IStringConverter<T> permits InputStreamConverter, OutputStreamConverter {

  @Override
  public final T convert(String value) {
    IOData ioData = IOData.parse(value);
    try {
      return createStream(ioData);
    } catch (IOException exc) {
      //TODO better exception
      throw new RuntimeException(exc);
    }
  }

  abstract T createStream(IOData data) throws IOException;
}
