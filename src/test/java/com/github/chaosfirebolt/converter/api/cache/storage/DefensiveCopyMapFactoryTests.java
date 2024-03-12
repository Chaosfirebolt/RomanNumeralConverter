package com.github.chaosfirebolt.converter.api.cache.storage;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class DefensiveCopyMapFactoryTests {

  @ParameterizedTest
  @MethodSource
  public void shouldReturnDifferentInstanceWithSameContent(Map<String, String> mapInstance) {
    Supplier<Map<String, String>> mapFactory = new DefensiveCopyMapFactory<>(() -> mapInstance);
    Map<String, String> result = mapFactory.get();
    assertNotSame(mapInstance, result, "Factory should have returned different map instance");
    assertEquals(mapInstance, result, "Defensive copy should have contained same data");
  }

  private static List<Map<String, String>> shouldReturnDifferentInstanceWithSameContent() {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("a", "b");

    LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("c", "d");

    TreeMap<String, String> treeMap = new TreeMap<>();
    treeMap.put("e", "f");

    ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
    concurrentHashMap.put("1", "2");

    Map<String, String> immutableMap1 = Map.of();

    Map<String, String> immutableMap2 = Map.of("11", "22");

    Map<String, String> immutableMap3 = Collections.unmodifiableMap(hashMap);
    return List.of(hashMap, linkedHashMap, treeMap, concurrentHashMap, immutableMap1, immutableMap2, immutableMap3);
  }
}
