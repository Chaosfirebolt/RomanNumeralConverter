package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.Parser;
import com.github.chaosfirebolt.converter.api.InvalidResultException;
import com.github.chaosfirebolt.converter.constants.IntegerType;

import java.util.EnumMap;

/**
 * Cache for parsers, using a map
 */
@Deprecated(since = "3.3.0", forRemoval = true)
public class MapParserCache extends MapCache<IntegerType, Parser> implements ParserCache {

  /**
   * Constructs new instance of Map based cache for parsers
   */
  public MapParserCache() {
    super(IntegerType::getParser, () -> new InvalidResultException("Returned value was null"), new EnumMap<>(IntegerType.class));
  }
}
