package com.github.chaosfirebolt.converter.api.initialization.source;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResourceFactoryTests {

  @Test
  public void classLoaderFactoryTest1() throws Exception {
    ClassLoaderResourceFactory factory = new ClassLoaderResourceFactory(ClassLoader.getSystemClassLoader());
    String path = "com/github/chaosfirebolt/converter/api/initialization/source/resource.txt";
    try (InputStream is = factory.create(path)) {
      assertNotNull(is, "Resource not found");
    }
  }

  @ParameterizedTest
  @ValueSource(strings = {"/com/github/chaosfirebolt/converter/api/initialization/source/resource.txt", "resource.txt", "/values-array.ser"})
  public void classFactoryTest1(String path) throws IOException {
    ClassResourceFactory factory = new ClassResourceFactory(getClass());
    try (InputStream is = factory.create(path)) {
      assertNotNull(is, "Resource not found");
    }
  }
}
