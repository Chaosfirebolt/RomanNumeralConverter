package com.chaos.converter.parser.impl;

import com.chaos.converter.RomanInteger;
import com.chaos.converter.constants.IntegerType;
import com.chaos.converter.util.Validator;

/**
 * Created by ChaosFire on 01-Mar-18
 */
public class Roman extends AbstractParser {

    @Override
    public RomanInteger parse(String number) {
        Validator.validateInput(number);
        Validator.validateRoman(number, IntegerType.ROMAN);
        throw new UnsupportedOperationException();
    }
}