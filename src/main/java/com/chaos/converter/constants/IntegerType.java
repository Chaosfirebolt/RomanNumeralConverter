package com.chaos.converter.constants;

/**
 * Created by ChaosFire on 01-Mar-18
 */
public enum IntegerType {

    ARABIC("Arabic"),
    ROMAN("Roman");

    private String parser;

    IntegerType(String parser) {
        this.parser = parser;
    }

    public String getParser() {
        return this.parser;
    }
}