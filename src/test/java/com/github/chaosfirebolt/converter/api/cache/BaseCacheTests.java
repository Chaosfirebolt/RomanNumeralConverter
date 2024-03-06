package com.github.chaosfirebolt.converter.api.cache;

import com.github.chaosfirebolt.converter.BiDirectionalMapRomanIntegerCache;
import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.InvalidResultException;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseCacheTests {

  @ParameterizedTest
  @MethodSource
  public void getValueWithNullKey_ShouldThrowNullPointerException(BaseCache<?, ?> cache) {
    assertExceptionIsThrownWithMessage(NullPointerException.class, () -> cache.getValue(null));
  }

  private static List<Arguments> getValueWithNullKey_ShouldThrowNullPointerException() {
    return List.of(Arguments.of(new MapParserCache()),
            Arguments.of(new BiDirectionalMapRomanIntegerCache(new MapParserCache())),
            Arguments.of(new TestingBaseCache()));
  }

  private static <T extends Exception> void assertExceptionIsThrownWithMessage(Class<T> expectedException, Executable actionToThrow) {
    T exception = assertThrows(expectedException, actionToThrow, () -> String.format("Should have thrown %s exception", expectedException.getSimpleName()));
    assertTrue(exception.getMessage() != null && !exception.getMessage().isEmpty(), "Exception should have had details message");
  }

  @ParameterizedTest
  @MethodSource
  public void returnNullValue_ShouldThrowInvalidResultException(BaseCache<Object, ?> cache) {
    assertExceptionIsThrownWithMessage(InvalidResultException.class, () -> cache.getValue(""));
  }

  private static List<Arguments> returnNullValue_ShouldThrowInvalidResultException() {
    return List.of(Arguments.of(new TestingBidirectionalCache()),
            Arguments.of(new TestingBaseCache()),
            Arguments.of(new TestingMapCache()));
  }

  private static final class TestingBaseCache extends BaseCache<Object, Object> {

    private TestingBaseCache() {
      super(obj -> null, () -> new InvalidResultException("message"));
    }

    @Override
    protected Optional<Object> computeIfAbsent(Object key, Function<Object, Object> computation) {
      return Optional.ofNullable(computation.apply(key));
    }

    @Override
    public void clear() {
    }
  }

  private static final class TestingMapCache extends MapCache<Object, Object> {

    private TestingMapCache() {
      super(Function.identity(), () -> new InvalidResultException("message"), new HashMap<>());
    }

    @Override
    protected Object cacheIfAbsent(Map<Object, Object> cache, Object key, Function<Object, Object> computation) {
      return null;
    }
  }

  private static final class TestingBidirectionalCache extends BiDirectionalMapRomanIntegerCache {

    public TestingBidirectionalCache() {
      super(new MapParserCache());
    }

    @Override
    protected RomanInteger cacheIfAbsent(Map<String, RomanInteger> cache, String key, Function<String, RomanInteger> computation) {
      return null;
    }
  }
}
