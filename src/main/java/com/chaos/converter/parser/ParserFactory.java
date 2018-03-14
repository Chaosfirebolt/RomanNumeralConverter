package com.chaos.converter.parser;

import com.chaos.converter.constants.IntegerType;
import com.chaos.converter.exc.InvalidParserException;
import com.chaos.converter.parser.impl.AbstractParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by ChaosFire on 01-Mar-18
 */
public class ParserFactory {

    private static final String PACKAGE = "com.chaos.converter.parser.impl.";
    private static final String ERROR_MESSAGE = "Invalid parser class.";

    @SuppressWarnings("unchecked")
    public static AbstractParser createParser(IntegerType integerType) {
        Class<AbstractParser> parserClass;
        try {
            parserClass = (Class<AbstractParser>) Class.forName(PACKAGE + integerType.getParser());
        } catch (ClassNotFoundException exc) {
            throw new InvalidParserException(ERROR_MESSAGE, exc);
        }
        Constructor<AbstractParser> constructor;
        try {
            constructor = parserClass.getDeclaredConstructor();
        } catch (NoSuchMethodException exc) {
            throw new InvalidParserException(ERROR_MESSAGE, exc);
        }
        AbstractParser instance;
        try {
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException exc) {
            throw new InvalidParserException(ERROR_MESSAGE, exc);
        }
        return instance;
    }
}