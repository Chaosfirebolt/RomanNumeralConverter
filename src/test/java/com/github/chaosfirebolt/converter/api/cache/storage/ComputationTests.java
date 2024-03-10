package com.github.chaosfirebolt.converter.api.cache.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ComputationTests {

  private int input;
  private int result;

  @BeforeEach
  public void setup() {
    this.input = 1;
    this.result = 2;
  }

  @Test
  public void wrapFunction_ShouldReturnSameResult() {
    Function<Integer, Integer> function = mock();
    when(function.apply(input)).thenReturn(result);

    Computation<Integer, Integer> computation = Computation.wrap(function);
    assertEquals(function.apply(input), computation.compute(input), "Computation should have returned same result as wrapped function");
    verify(function, times(2)).apply(input);
  }

  @Test
  public void staticUnwrapFunction_ShouldReturnSameResult() {
    Computation<Integer, Integer> computation = mock();
    when(computation.compute(input)).thenReturn(result);

    Function<Integer, Integer> function = Computation.unwrap(computation);
    assertEquals(computation.compute(input), function.apply(input), "Function should have returned same result as unwrapped function");
    verify(computation, times(2)).compute(input);
  }

  @Test
  public void unwrapFunction_ShouldReturnSameResult() {
    Computation<Integer, Integer> computation = spy(new FunctionAdapter<>(i -> i + 1));
    when(computation.compute(input)).thenReturn(result);

    Function<Integer, Integer> function = computation.unwrap();
    assertEquals(computation.compute(input), function.apply(input), "Function should have returned same result as unwrapped function");
    //check only manual invocation, the adapter unwraps the underlying function
    verify(computation).compute(input);
  }
}
