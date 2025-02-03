package com.github.chaosfirebolt.converter.exec.converter;

import com.beust.jcommander.IStringConverter;
import com.github.chaosfirebolt.converter.exec.io.Delimiter;

/**
 * Converter from {@link String} to a {@link Delimiter}.
 */
public class DelimiterConverter implements IStringConverter<Delimiter> {

  @Override
  public Delimiter convert(String value) {
    return Delimiter.of(value);
  }
}
