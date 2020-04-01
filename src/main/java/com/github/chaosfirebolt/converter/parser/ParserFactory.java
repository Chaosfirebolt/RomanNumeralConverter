package com.github.chaosfirebolt.converter.parser;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.exc.InvalidParserException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by ChaosFire on 01-Mar-18
 *
 * Factory class, providing instances of {@link AbstractParser}.
 */
public class ParserFactory {

    /**
     * Package containing parser classes.
     */
    private static final String PACKAGE = "com.github.chaosfirebolt.converter.parser.impl.";
    private static final String ERROR_MESSAGE = "Invalid parser class.";

    /**
     * Creates instances of {@link AbstractParser}.
     *
     * @param integerType type of integer, whose parser is needed.
     * @return instance of {@link AbstractParser} for this type of {@link IntegerType}
     */
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