package com.github.chaosfirebolt.converter.testUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {

    private Constants() {
        //no instances allowed
    }

    public static final List<String> FIELD_NAMES = Collections.unmodifiableList(Arrays.asList("romanRepresentation", "arabicRepresentation", "arithmeticMode"));
}