package com.github.chaosfirebolt.converter.exec.converter;

import com.github.chaosfirebolt.converter.exec.io.IOType;

record IOData(IOType type, String path) {

  private static final String DELIMITER = "=";

  static IOData parse(String value) {
    int delimIndex = value.indexOf(DELIMITER);
    String type;
    String path = null;
    if (delimIndex == -1) {
      type = value;
    } else {
      type = value.substring(0, delimIndex);
      path = value.substring(delimIndex + 1);
    }
    IOType ioType = parseType(type);
    return new IOData(ioType, path);
  }

  private static IOType parseType(String type) {
    try {
      return IOType.valueOf(type);
    } catch (IllegalArgumentException exc) {
      return IOType.fromOption(type);
    }
  }
}
