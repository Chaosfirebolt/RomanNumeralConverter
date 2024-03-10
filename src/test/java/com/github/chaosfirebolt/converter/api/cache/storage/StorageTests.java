package com.github.chaosfirebolt.converter.api.cache.storage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
      assertTrue(storedValue.isPresent(), () -> "Miss8ng value for key - " + key);
      assertEquals(key, storedValue.get(), "Incorrect stored value");
    }
  }
}
