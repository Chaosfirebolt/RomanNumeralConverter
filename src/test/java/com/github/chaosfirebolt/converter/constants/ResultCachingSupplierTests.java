package com.github.chaosfirebolt.converter.constants;

import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class ResultCachingSupplierTests {

  private static final String ERROR_MESSAGE = "Caching supplier did not return same result on subsequent invocation";

  @Test
  public void eachInvocationShouldReturnSameResult() {
    ResultCachingSupplier<Object> supplier = new ResultCachingSupplier<>(Object::new, () -> false);
    Object firstResult = supplier.get();
    for (int i = 0; i < 100; i++) {
      assertSame(firstResult, supplier.get(), ERROR_MESSAGE);
    }
  }

  @Test
  public void shouldRecalculateWhenConditionIsMet() {
    Supplier<Object> spiedFactory = spy(TestFactory.class);

    BooleanSupplier recalculateCondition = mock();
    when(recalculateCondition.getAsBoolean()).thenReturn(false, true);

    ResultCachingSupplier<Object> supplier = new ResultCachingSupplier<>(spiedFactory, recalculateCondition);

    Object firstResult = supplier.get();
    verify(recalculateCondition, never()).getAsBoolean();
    verify(spiedFactory, times(1)).get();

    Object secondResult = supplier.get();
    verify(recalculateCondition).getAsBoolean();
    verifyNoMoreInteractions(spiedFactory);
    assertSame(firstResult, secondResult, ERROR_MESSAGE);

    Object thirdResult = supplier.get();
    verify(recalculateCondition, times(2)).getAsBoolean();
    verify(spiedFactory, times(2)).get();
    assertNotSame(firstResult, thirdResult, "Caching supplier did not recalculate the value");
  }

  private static class TestFactory implements Supplier<Object> {

    @Override
    public Object get() {
      return new Object();
    }
  }
}
