package com.github.chaosfirebolt.converter.api.cache.storage;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * Map based storage implementation.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class MapStorage<K, V> implements Storage<K, V> {

  private static final String KEY_ERROR = "Null key";
  private static final String VALUE_ERROR = "Null value";

  private final Map<K, V> map;

  /**
   * Creates a new storage with the supplied map. If the map is not empty, a defensive copy of the map will be created.
   *
   * @param map map to initialize the storage with
   */
  public MapStorage(Map<K, V> map) {
    this.map = defensiveCopy(map);
  }

  private static <K, V> Map<K, V> defensiveCopy(Map<K, V> map) {
    try {
      @SuppressWarnings("unchecked")
      Map<K, V> copy = map.getClass().getConstructor(Map.class).newInstance(map);
      return copy;
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exc) {
      return new HashMap<>(map);
    }
  }

  /**
   * Creates a new storage with a default map implementation.
   */
  public MapStorage() {
    this.map = new HashMap<>();
  }

  @Override
  public void store(K key, V value) {
    map.put(Objects.requireNonNull(key, KEY_ERROR), Objects.requireNonNull(value, VALUE_ERROR));
  }

  @Override
  public Optional<V> retrieve(K key) {
    V storedValue = map.get(Objects.requireNonNull(key, KEY_ERROR));
    return Optional.ofNullable(storedValue);
  }

  @Override
  public V compute(K key, Computation<K, V> computation) {
    Objects.requireNonNull(key, KEY_ERROR);
    Function<K, V> adaptedFunction = Objects.requireNonNull(computation, "Null computation").unwrap();
    V value = map.computeIfAbsent(key, adaptedFunction);
    return Objects.requireNonNull(value, "Null computed value");
  }

  @Override
  public void remove(K key) {
    map.remove(Objects.requireNonNull(key, KEY_ERROR));
  }

  @Override
  public void clear() {
    map.clear();
  }
}
