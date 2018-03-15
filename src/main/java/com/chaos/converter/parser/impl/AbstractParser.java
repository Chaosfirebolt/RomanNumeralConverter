package com.chaos.converter.parser.impl;

import com.chaos.converter.util.DataTransferObject;

/**
 * Created by ChaosFire on 01-Mar-18
 */
public abstract class AbstractParser {

    AbstractParser() {
    }

    public abstract DataTransferObject parse(String number);
}