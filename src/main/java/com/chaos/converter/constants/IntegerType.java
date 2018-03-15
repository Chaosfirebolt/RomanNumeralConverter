package com.chaos.converter.constants;

/**
 * Created by ChaosFire on 01-Mar-18
 *
 * Enumeration for supported integer types.
 */
public enum IntegerType {

    /**
     * Representation for arabic format integers.
     */
    ARABIC("ArabicParser"),
    /**
     * Representation for roman format integers.
     */
    ROMAN("RomanParser");

    /**
     * Name of the class, parsing strings to objects containing arabic and roman representation of the same number.
     */
    private String parser;

    IntegerType(String parser) {
        this.parser = parser;
    }

    public String getParser() {
        return this.parser;
    }
}