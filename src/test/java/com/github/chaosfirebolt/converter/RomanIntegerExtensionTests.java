package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.util.PairMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;

public class RomanIntegerExtensionTests {

  private MockedStatic<PairMap> mockedStatic;
  private PairMap spiedPairs;

  @BeforeEach
  public void setup() {
    this.spiedPairs = spy(PairMap.getInstance());
    this.mockedStatic = mockStatic(PairMap.class);

    mockedStatic.when(PairMap::getInstance).thenReturn(spiedPairs);
  }

  @AfterEach
  public void cleanUp() {
    mockedStatic.close();
    spiedPairs.clearAdditionalOrders();
  }

  @ParameterizedTest
  @CsvSource({"a, b", "e, f", "y, z"})
  public void extendShouldCallRegistration(char symbolFive, char symbolTen) {
    RomanInteger.extend(symbolFive, symbolTen);
    verify(spiedPairs).registerNextOrder(symbolFive, symbolTen);
  }

  @Test
  public void clearExtensionShouldCallClearOrders() {
    RomanInteger.clearExtensions();
    verify(spiedPairs).clearAdditionalOrders();
  }
}
