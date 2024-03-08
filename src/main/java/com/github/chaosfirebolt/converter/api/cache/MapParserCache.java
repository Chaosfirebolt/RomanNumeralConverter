package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.Parser;
import com.github.chaosfirebolt.converter.api.InvalidResultException;
import com.github.chaosfirebolt.converter.api.cache.storage.MapStorage;
import com.github.chaosfirebolt.converter.constants.IntegerType;

import java.util.EnumMap;

/**
 * Cache for parsers, using a map
 */
public class MapParserCache extends DefaultCache<IntegerType, Parser> implements ParserCache {

  /**
   * Constructs new instance of Map based cache for parsers
   */
  public MapParserCache() {
    super(new MapStorage<>(new EnumMap<>(IntegerType.class)), IntegerType::getParser, () -> new InvalidResultException("Returned value was null"));
  }
}
