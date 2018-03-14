package com.chaos.converter.parser;

import com.chaos.converter.constants.IntegerType;
import com.chaos.converter.parser.impl.AbstractParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChaosFire on 14.3.2018 г.
 */
public class ParserContainer {

    private static ParserContainer instance;

    private Map<IntegerType, AbstractParser> parserMap;

    private ParserContainer() {
        this.parserMap = new HashMap<>();
    }

    public static ParserContainer getInstance() {
        if (instance == null) {
            instance = new ParserContainer();
        }
        return instance;
    }

    public AbstractParser getParser(IntegerType type) {
        AbstractParser parser = this.parserMap.get(type);
        if (parser == null) {
            parser = ParserFactory.createParser(type);
            this.parserMap.put(type, parser);
        }
        return parser;
    }
}