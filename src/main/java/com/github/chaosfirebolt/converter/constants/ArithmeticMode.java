package com.github.chaosfirebolt.converter.constants;

/**
 * Created by ChaosFire on 11/30/2021
 *
 * Mode defining behaviour when result from arithmetic operations is invalid
 */
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