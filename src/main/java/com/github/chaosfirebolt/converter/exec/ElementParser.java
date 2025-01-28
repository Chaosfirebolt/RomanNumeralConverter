package com.github.chaosfirebolt.converter.exec;

import com.github.chaosfirebolt.converter.RomanInteger;

import java.util.function.Function;

class ElementParser implements Function<String, String> {

  private final Function<String, RomanInteger> romanIntegerParser;
  private final Function<RomanInteger, String> successHandler;
  private final Function<Exception, String> errorHandler;

  ElementParser(Function<String, RomanInteger> romanIntegerParser, Function<RomanInteger, String> successHandler, Function<Exception, String> errorHandler) {
    this.romanIntegerParser = romanIntegerParser;
    this.successHandler = successHandler;
    this.errorHandler = errorHandler;
  }

  @Override
  public String apply(String element) {
    try {
      RomanInteger parsed = romanIntegerParser.apply(element);
      return successHandler.apply(parsed);
    } catch (Exception exc) {
      return errorHandler.apply(exc);
    }
  }
}
