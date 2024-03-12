package com.github.chaosfirebolt.converter.api.cache.storage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StorageTests {

  private static final Set<String> EXISTING_KEYS = Set.of("a", "b", "c", "d", "e");
  private static final Set<String> NOT_EXISTING_KEYS = Set.of("f", "g", "h");

  private static List<Storage<String, String>> storages() {
    return List.of(MapStorage.lenient(StorageTests::sourceMap), MapStorage.strict(StorageTests::sourceMap));
  }

  private static Map<String, String> sourceMap() {
    return EXISTING_KEYS.stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
  }

  @ParameterizedTest
  @MethodSource("storages")
  public void retrieve_ShouldReturnCorrectForExistingKey(Storage<String, String> storage) {
    for (String key : EXISTING_KEYS) {
      Optional<String> storedValue = storage.retrieve(key);
      assertTrue(storedValue.isPresent(), () -> "Missing value for key - " + key);
      assertEquals(key, storedValue.get(), "Incorrect stored value");
    }
  }

  @ParameterizedTest
  @MethodSource("storages")
  public void retrieve_ShouldReturnEmptyForNotExistingKey(Storage<String, String> storage) {
    for (String key : NOT_EXISTING_KEYS) {
      Optional<String> storedValue = storage.retrieve(key);
      assertTrue(storedValue.isEmpty(), () -> "Found value for key - " + key);
    }
  }

  @ParameterizedTest
  @MethodSource("storages")
  public void shouldThrowNpeForNullKey(Storage<String, String> storage) {
    NullPointerException npe = assertThrows(NullPointerException.class, () -> storage.retrieve(null), "Should have thrown NPE when retrieving null key");
    assertExceptionHasMessage(npe);
  }

  private static void assertExceptionHasMessage(Exception exception) {
    final String errMsg = "Missing exception message";
    String exceptionMessage = exception.getMessage();
    assertNotNull(exceptionMessage, errMsg);
    assertFalse(exceptionMessage.isBlank(), errMsg);
  }

  @ParameterizedTest
  @MethodSource("storages")
  public void store_ValueShouldBeStored(Storage<String, String> storage) {
    for (String key : NOT_EXISTING_KEYS) {
      assertTrue(storage.retrieve(key).isEmpty(), "Key should not exist");

      String randomValue = UUID.randomUUID().toString();
      storage.store(key, randomValue);

      Optional<String> storedValue = storage.retrieve(key);
      assertTrue(storedValue.isPresent(), () -> "Missing value for key - " + key);
      assertEquals(randomValue, storedValue.get(), "Incorrect stored value");
    }
  }

  @ParameterizedTest
  @MethodSource("storages")
  public void store_NullKey_ShouldThrowNpe(Storage<String, String> storage) {
    NullPointerException npe = assertThrows(NullPointerException.class, () -> storage.store(null, "asd"));
    assertExceptionHasMessage(npe);
  }

  @ParameterizedTest
  @MethodSource("storages")
  public void store_NullValue_ShouldThrowNpe(Storage<String, String> storage) {
    NullPointerException npe = assertThrows(NullPointerException.class, () -> storage.store("qwerty", null));
    assertExceptionHasMessage(npe);
  }

  @ParameterizedTest
  @MethodSource("allStorages")
  public void compute_KeyExists_ShouldNotInvokeComputation(Storage<String, String> storage) {
    Computation<String, String> computation = mock();
    configureComputationMock(computation, EXISTING_KEYS);
    for (String key : EXISTING_KEYS) {
      String result = storage.compute(key, computation);
      assertEquals(key, result, "Value should not have been computed");
      verify(computation, never()).compute(key);
    }
  }

  private static void configureComputationMock(Computation<String, String> mock, Set<String> keys) {
    when(mock.unwrap()).thenReturn(mock::compute);
    for (String key : keys) {
      when(mock.compute(key)).thenAnswer(invocation -> {
        String input = invocation.getArgument(0);
        return input.repeat(10);
      });
    }
  }

  private static List<Storage<String, String>> allStorages() {
    List<Storage<String, String>> storages = new ArrayList<>(storages());
    storages.add(new TestStorage(sourceMap()));
    return storages;
  }

  private record TestStorage(Map<String, String> map) implements Storage<String, String> {

    @Override
    public void store(String key, String value) {
      map.putIfAbsent(key, value);
    }

    @Override
    public Optional<String> retrieve(String key) {
      return Optional.ofNullable(map.get(key));
    }

    @Override
    public void remove(String key) {
      map.remove(key);
    }

    @Override
    public void clear() {
      map.clear();
    }
  }

  @ParameterizedTest
  @MethodSource("allStorages")
  public void compute_KeyDoesNotExist_ShouldInvokeComputation(Storage<String, String> storage) {
    Computation<String, String> computation = mock();
    configureComputationMock(computation, NOT_EXISTING_KEYS);
    for (String key : NOT_EXISTING_KEYS) {
      String result = storage.compute(key, computation);
      String expectedValue = key.repeat(10);

      assertEquals(expectedValue, result, "Value should have been computed");
      verify(computation).compute(key);

      Optional<String> extracted = storage.retrieve(key);
      assertTrue(extracted.isPresent(), "No value found for key");
      assertEquals(expectedValue, extracted.get(), "Unexpected value retrieved");
    }
  }

  @ParameterizedTest
  @MethodSource("allStorages")
  public void compute_NullKey_ShouldThrowNpe(Storage<String, String> storage) {
    NullPointerException npe = assertThrows(NullPointerException.class, () -> storage.compute(null, s -> s.repeat(11)));
    assertExceptionHasMessage(npe);
  }

  @ParameterizedTest
  @MethodSource("allStorages")
  public void compute_NullComputation_ShouldThrowNpe(Storage<String, String> storage) {
    NullPointerException npe = assertThrows(NullPointerException.class, () -> storage.compute("asd", null));
    assertExceptionHasMessage(npe);
  }

  @ParameterizedTest
  @MethodSource("allStorages")
  public void compute_ComputationReturnsNull_ShouldThrowNpe(Storage<String, String> storage) {
    NullPointerException npe = assertThrows(NullPointerException.class, () -> storage.compute("qwe", s -> null));
    assertExceptionHasMessage(npe);
  }

  @ParameterizedTest
  @MethodSource("storages")
  public void remove_ShouldRemove(Storage<String, String> storage) {
    for (String key : EXISTING_KEYS) {
      Optional<String> storedValue = storage.retrieve(key);
      assertTrue(storedValue.isPresent(), "Expected value not found");

      storage.remove(key);
      storedValue = storage.retrieve(key);
      assertTrue(storedValue.isEmpty(), "Stored value should have been removed");
    }
  }

  @ParameterizedTest
  @MethodSource("storages")
  public void remove_NullKey_ShouldThrowNpe(Storage<String, String> storage) {
    NullPointerException npe = assertThrows(NullPointerException.class, () -> storage.remove(null));
    assertExceptionHasMessage(npe);
  }

  @ParameterizedTest
  @MethodSource("storages")
  public void clear_ShouldRemoveAllStored(Storage<String, String> storage) {
    for (String key : EXISTING_KEYS) {
      Optional<String> storedValue = storage.retrieve(key);
      assertTrue(storedValue.isPresent(), "Expected value not found");
    }
    storage.clear();
    for (String key : EXISTING_KEYS) {
      Optional<String> storedValue = storage.retrieve(key);
      assertTrue(storedValue.isEmpty(), "Value should not have been found");
    }
  }
}
