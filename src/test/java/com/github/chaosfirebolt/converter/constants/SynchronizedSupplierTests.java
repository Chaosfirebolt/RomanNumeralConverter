package com.github.chaosfirebolt.converter.constants;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.mockito.Mockito.*;

public class SynchronizedSupplierTests {

  @Test
  public void shouldInvokeDelegate() {
    Supplier<Object> mockedDelegate = mock();
    SynchronizedSupplier<Object> synchronizedSupplier = new SynchronizedSupplier<>(mockedDelegate);
    for (int i = 0; i < 100; i++) {
      synchronizedSupplier.get();
      int expectedInvocations = i + 1;
      verify(mockedDelegate, times(expectedInvocations)).get();
    }
  }
}
