package com.github.chaosfirebolt.converter;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.util.PairMap;
import com.github.chaosfirebolt.converter.util.PairMapping;

sealed abstract class BaseParser implements Parser permits ArabicParser, RomanParser {

  /**
   * The integer type this parser deals with.
   */
  protected final IntegerType integerType;
  
  /**
   * Mapping of roman to arabic numeral symbol pairs.
   */
  protected final PairMapping pairMapping;

  protected BaseParser(IntegerType integerType) {
    this.integerType = integerType;
    this.pairMapping = PairMap.getInstance();
  }
}
