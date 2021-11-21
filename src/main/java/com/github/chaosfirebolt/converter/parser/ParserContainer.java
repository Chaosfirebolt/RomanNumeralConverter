package com.github.chaosfirebolt.converter.parser;

import com.github.chaosfirebolt.converter.constants.IntegerType;
import com.github.chaosfirebolt.converter.parser.impl.AbstractParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChaosFire on 14.3.2018 г.
 *
 * Singleton class, holding necessary implementations of {@link AbstractParser}
 * @see AbstractParser
 */
public class ParserContainer {

    /**
     * Sole instance of the class.
     */
    private static ParserContainer instance;

    /**
     * Holds integer types and their corresponding parser implementations.
     */
    private final Map<IntegerType, AbstractParser> parserMap;

    private ParserContainer() {
        this.parserMap = new HashMap<>();
    }

    public static ParserContainer getInstance() {
        if (instance == null) {
            instance = new ParserContainer();
        }
        return instance;
    }

    /**
     * Getter method for parsers. Instances of parsers are not created, unless asked for by this method.
     *
     * @param type Integer type, whose corresponding parser is needed.
     * @return implementation of {@link AbstractParser}, corresponding to provided {@link IntegerType}
     */
    public AbstractParser getParser(IntegerType type) {
        AbstractParser parser = this.parserMap.get(type);
        if (parser == null) {
            parser = ParserFactory.createParser(type);
            this.parserMap.put(type, parser);
        }
        return parser;
    }
}