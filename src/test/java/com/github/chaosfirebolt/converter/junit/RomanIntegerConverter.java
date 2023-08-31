package com.github.chaosfirebolt.converter.junit;

import com.github.chaosfirebolt.converter.RomanInteger;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ArgumentConverter;

public class RomanIntegerConverter implements ArgumentConverter {

    @Override
    public Object convert(Object o, ParameterContext parameterContext) throws ArgumentConversionException {
        if (!(o instanceof String)) {
            throw new ArgumentConversionException("Argument should be a string");
        }
        return RomanInteger.parse(o.toString());
    }
}
