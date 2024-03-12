package com.github.chaosfirebolt.converter.api.cache.storage;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Decorates a factory to create a defensive copy of the generated map.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class DefensiveCopyMapFactory<K, V> implements Supplier<Map<K, V>> {

  private final Supplier<Map<K, V>> mapFactory;
  private final Function<Map<K, V>, Map<K, V>> onErrorFallback;

  /**
   * @param mapFactory      factory to generate a map
   * @param onErrorFallback fallback to invoke, if creating the defensive copy fails
   */
  public DefensiveCopyMapFactory(Supplier<Map<K, V>> mapFactory, Function<Map<K, V>, Map<K, V>> onErrorFallback) {
    this.mapFactory = mapFactory;
    this.onErrorFallback = onErrorFallback;
  }

  /**
   * Provides a default {@link DefensiveCopyMapFactory#onErrorFallback}.
   *
   * @param mapFactory factory to generate a map
   */
  public DefensiveCopyMapFactory(Supplier<Map<K, V>> mapFactory) {
    this(mapFactory, HashMap::new);
  }

  @Override
  public Map<K, V> get() {
    Map<K, V> map = mapFactory.get();
    try {
      @SuppressWarnings("unchecked")
      Map<K, V> copy = map.getClass().getConstructor(Map.class).newInstance(map);
      return copy;
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException exc) {
      return onErrorFallback.apply(map);
    }
  }
}
