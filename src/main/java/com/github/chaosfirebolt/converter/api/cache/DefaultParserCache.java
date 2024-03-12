package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.Parser;
import com.github.chaosfirebolt.converter.api.OperationFailure;
import com.github.chaosfirebolt.converter.api.cache.storage.MapStorage;
import com.github.chaosfirebolt.converter.api.initialization.NoOpMapData;
import com.github.chaosfirebolt.converter.constants.IntegerType;

import java.util.EnumMap;

/**
 * Default parser cache implementation
 */
public final class DefaultParserCache extends DefaultCache<IntegerType, Parser> implements ParserCache {

  /**
   * Creates new instance with default parameters
   */
  public DefaultParserCache() {
    super(MapStorage.lenient(() -> new EnumMap<>(IntegerType.class)), IntegerType::getParser, new NoOpMapData<>(), () -> new OperationFailure("Caching failure"));
  }
}
