package com.github.chaosfirebolt.converter.constants;

/**
 * Mode defining behaviour when result from arithmetic operations is invalid
 * @deprecated since 2.0.2
 */
@Deprecated
public enum ArithmeticMode {

    /**
     * Strict arithmetic mode is signal for throwing exception when result is invalid
     */
    STRICT,
    /**
     * Loose arithmetic mode is signal to return null when result is invalid
     */
    LOOSE
}