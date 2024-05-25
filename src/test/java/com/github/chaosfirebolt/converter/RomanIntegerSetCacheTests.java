package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCache;
import com.github.chaosfirebolt.converter.api.cache.RomanIntegerCacheFactory;
import com.github.chaosfirebolt.converter.api.initialization.InitializationCapable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RomanIntegerSetCacheTests {

  @AfterAll
  public static void cleanup() {
    RomanInteger.disableCache();
  }

  @Test
  public void nonInitializableCache_ShouldInvokeCreateCache() {
    RomanIntegerCache cache = mock(RomanIntegerCache.class);
    RomanIntegerCacheFactory cacheFactory = mock(RomanIntegerCacheFactory.class);
    when(cacheFactory.createCache(any())).thenReturn(cache);

    RomanInteger.setCache(cacheFactory);
    verify(cacheFactory).createCache(any());
  }

  @Test
  public void initializableCache_ShouldInvokeCreateCache() {
    RomanIntegerCache cache = mock(RomanIntegerCache.class, withSettings().extraInterfaces(InitializationCapable.class));
    RomanIntegerCacheFactory cacheFactory = mock(RomanIntegerCacheFactory.class);
    when(cacheFactory.createCache(any())).thenReturn(cache);

    RomanInteger.setCache(cacheFactory);
    verify(cacheFactory).createCache(any());
    InitializationCapable castedCache = (InitializationCapable) cache;
    verify(castedCache).initialize();
  }

  @Test
  public void oldCacheShouldBeCleared() {
    RomanIntegerCache oldCache = mock(RomanIntegerCache.class, withSettings().extraInterfaces(InitializationCapable.class));
    RomanIntegerCacheFactory cacheFactory = mock(RomanIntegerCacheFactory.class);
    when(cacheFactory.createCache(any())).thenReturn(oldCache, mock(RomanIntegerCache.class));

    RomanInteger.setCache(cacheFactory);
    RomanInteger.setCache(cacheFactory);
    verify(oldCache).clear();
  }
}
