package com.github.chaosfirebolt.converter.constants;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.IntSupplier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RecalculateByMapSizeTests {

  private final IntSupplier sizeSupplier = mock(IntSupplier.class);
  private final RecalculateByMapSize recalculateByMapSize = new RecalculateByMapSize(sizeSupplier);

  @BeforeEach
  public void setUp() {
    when(sizeSupplier.getAsInt()).thenReturn(1, 1, 3);
  }

  @Test
  public void initialInvocationShouldReturnTrue() {
    assertFirstInvocation();
  }

  private void assertFirstInvocation() {
    boolean shouldRecalculate = recalculateByMapSize.getAsBoolean();
    assertTrue(shouldRecalculate, "Initial invocation should return true");
    verify(sizeSupplier).getAsInt();
  }

  @Test
  public void testConsecutiveInvocations() {
    assertFirstInvocation();

    boolean secondResult = recalculateByMapSize.getAsBoolean();
    assertFalse(secondResult, "Incorrect result for 2nd invocation");
    verify(sizeSupplier, times(2)).getAsInt();

    boolean thirdResult = recalculateByMapSize.getAsBoolean();
    assertTrue(thirdResult, "Incorrect result for 3rd invocation");
    verify(sizeSupplier, times(3)).getAsInt();
  }
}
