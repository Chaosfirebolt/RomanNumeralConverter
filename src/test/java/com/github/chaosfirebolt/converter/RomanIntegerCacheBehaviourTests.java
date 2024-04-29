package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.DefaultParserCache;
import com.github.chaosfirebolt.converter.api.cache.ParserCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;
import com.github.chaosfirebolt.converter.api.cache.storage.MapStorage;
import com.github.chaosfirebolt.converter.api.cache.storage.Storage;
import com.github.chaosfirebolt.converter.api.initialization.InitializationData;
import com.github.chaosfirebolt.converter.api.initialization.NoOpMapData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class RomanIntegerCacheBehaviourTests {

  private ParserCache parserCache;
  private Storage<String, RomanInteger> storage;

  @BeforeEach
  public void setup() {
    this.parserCache = spy(DefaultParserCache.class);
    this.storage = spy(MapStorage.lenient(HashMap::new));
  }

  @ParameterizedTest
  @ValueSource(strings = {"11", "1234", "321"})
  public void getValue_DefaultCache(String value) {
    RomanIntegerCache cache = createRomanIntegerCache(DefaultRomanIntegerCache::new);
    cache.getValue(value);
    verify(parserCache).getValue(any());
    verify(storage).compute(eq(value), any());
  }

  @ParameterizedTest
  @ValueSource(strings = {"11", "1234", "321"})
  public void getValue_UniDirectionalCache(String value) {
    RomanIntegerCache cache = createRomanIntegerCache(UniDirectionalRomanIntegerCache::new);
    cache.getValue(value);
    verify(parserCache).getValue(any());
    verify(storage).compute(eq(value), any());
  }

  @ParameterizedTest
  @ValueSource(strings = {"11", "1234", "321"})
  public void getValue_BiDirectionalCache(String value) {
    RomanIntegerCache cache = createRomanIntegerCache(BiDirectionalRomanIntegerCache::new);
    RomanInteger result = cache.getValue(value);
    verify(parserCache).getValue(any());

    verify(storage).retrieve(eq(value));
    verify(storage).store(eq(result.getRoman()), eq(result));
    verify(storage).store(eq(Integer.toString(result.getArabic())), eq(result));
  }

  @Test
  public void clear_DefaultCache() {
    RomanIntegerCache cache = createRomanIntegerCache(DefaultRomanIntegerCache::new);
    verifyClearStorageInvokedOnClearCache(cache);
  }

  private void verifyClearStorageInvokedOnClearCache(RomanIntegerCache cache) {
    cache.clear();
    verify(storage).clear();
  }

  @Test
  public void clear_UniDirectionalCache() {
    RomanIntegerCache cache = createRomanIntegerCache(UniDirectionalRomanIntegerCache::new);
    verifyClearStorageInvokedOnClearCache(cache);
  }

  @Test
  public void clear_BiDirectionalCache() {
    RomanIntegerCache cache = createRomanIntegerCache(BiDirectionalRomanIntegerCache::new);
    verifyClearStorageInvokedOnClearCache(cache);
  }

  private RomanIntegerCache createRomanIntegerCache(CacheFactory cacheFactory) {
    return cacheFactory.create(parserCache, storage, new NoOpMapData<>());
  }

  private interface CacheFactory {

    RomanIntegerCache create(ParserCache parserCache, Storage<String, RomanInteger> storage, InitializationData<Map<String, RomanInteger>> initializationData);
  }
}
