package com.chaos.converter.parser.impl;

import com.chaos.converter.RomanInteger;
import com.chaos.converter.exc.ConstructionException;

import java.lang.reflect.Constructor;

/**
 * Created by ChaosFire on 01-Mar-18
 */
public abstract class AbstractParser {

    static final Constructor<RomanInteger> ROMAN_INTEGER_CONSTRUCTOR = getConstructor();

    AbstractParser() {
    }

    public abstract RomanInteger parse(String number);

    private static Constructor<RomanInteger> getConstructor() {
        try {
            return RomanInteger.class.getDeclaredConstructor(String.class, Integer.class);
        } catch (NoSuchMethodException exc) {
            throw new ConstructionException("Can't find RomanInteger constructor.", exc);
        }
    }
}