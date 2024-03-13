package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.api.OperationFailure;
import com.github.chaosfirebolt.converter.api.cache.storage.Computation;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;
import com.github.chaosfirebolt.converter.api.initialization.InitializationData;
import com.github.chaosfirebolt.converter.api.initialization.NoOpMapData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DefaultCacheTests {

  private static final String UNEXPECTED_EXCEPTION = "Expected exception not thrown";

  private final Storage<String, String> storage;
  private final Computation<String, String> computation;
  private final InitializationData<Map<String, String>> initializationData;
  private final Supplier<RuntimeException> exceptionSupplier;
  private final Extraction<String, String> extraction;

  private DefaultCache<String, String> cache;

  public DefaultCacheTests() {
    this.storage = mock();
    this.computation = mock();
    this.initializationData = spy(new NoOpMapData<>());
    this.exceptionSupplier = mock();
    this.extraction = spy(new DefaultExtraction());
  }

  private static final class DefaultExtraction implements Extraction<String, String> {

    @Override
    public String extract(Storage<String, String> storage, String key, Computation<String, String> computation) {
      return storage.compute(key, computation);
    }
  }

  @BeforeEach
  public void setup() {
    when(storage.compute(any(), any())).thenCallRealMethod();

    when(computation.compute(any())).thenAnswer(invocation -> {
      String arg = invocation.getArgument(0);
      return valueFromKey(arg);
    });

    when(exceptionSupplier.get()).thenReturn(new OperationFailure("something went wrong"));

    this.cache = spy(new DefaultCache<>(storage, computation, initializationData, exceptionSupplier, extraction));
  }

  private static String valueFromKey(String key) {
    return key.repeat(3);
  }

  @Test
  public void noKey_ShouldInvokeComputation() {
    String key = "a";
    String result = cache.getValue(key);

    verify(computation).compute(key);
    assertEquals(valueFromKey(key), result, "Unexpected result");
  }

  @Test
  public void keyExists_ShouldNotInvokeComputation() {
    String key = "b";
    when(storage.retrieve(key)).thenReturn(Optional.of(valueFromKey(key)));

    String expectedResult = valueFromKey(key);
    String actualResult = cache.getValue(key);

    verify(computation, never()).compute(key);
    assertEquals(expectedResult, actualResult, "Unexpected result");
  }

  @Test
  public void npeForWhateverReason_ShouldBeRethrown() {
    NullPointerException expectedException = new NullPointerException("msg");
    doThrow(expectedException).when(extraction).extract(any(), any(), any());

    NullPointerException thrownException = assertThrows(NullPointerException.class, () -> cache.getValue("qwerty"), UNEXPECTED_EXCEPTION);
    assertSame(expectedException, thrownException, "Null pointer exception was not rethrown");

    verify(extraction).extract(any(), any(), any());
    verify(exceptionSupplier, never()).get();
  }

  @Test
  public void illegalArgumentExceptionForWhateverReason_ShouldBeRethrown() {
    IllegalArgumentException expectedException = new IllegalArgumentException("msg");
    doThrow(expectedException).when(extraction).extract(any(), any(), any());

    IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> cache.getValue("qwerty"), UNEXPECTED_EXCEPTION);
    assertSame(expectedException, thrownException, "Illegal argument exception was not rethrown");

    verify(extraction).extract(any(), any(), any());
    verify(exceptionSupplier, never()).get();
  }

  @ParameterizedTest
  @MethodSource("exceptions")
  public void someException_ShouldBeWrappedAndReThrownByExceptionSupplier(Exception realException) {
    doThrow(realException).when(extraction).extract(any(), any(), any());

    OperationFailure thrownException = assertThrows(OperationFailure.class, () -> cache.getValue("qwerty"), UNEXPECTED_EXCEPTION);
    assertNotSame(realException, thrownException, "Incorrect exception thrown");
    assertSame(realException, thrownException.getCause(), "Real exception should have been cause");

    verify(extraction).extract(any(), any(), any());
    verify(exceptionSupplier).get();
  }

  private static List<Exception> exceptions() {
    return List.of(new RuntimeException("asd"), new IllegalStateException("asd"), new ArithmeticException("asd"), new ArrayIndexOutOfBoundsException("asd"), new NoSuchElementException("asd"));
  }

  @Test
  public void clearCache_ShouldClearTheStorage() {
    cache.clear();
    verify(storage).clear();
  }

  @Test
  public void initializeCache_ShouldStoreEntriesAndCleanup() {
    when(initializationData.getData()).thenReturn(Map.of("a", "a"));
    cache.initialize();

    verify(initializationData).getData();
    verify(storage).store(any(), any());
    verify(initializationData).cleanup();
  }
}
