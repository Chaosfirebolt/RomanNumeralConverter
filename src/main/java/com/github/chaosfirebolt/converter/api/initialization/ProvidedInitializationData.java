package com.github.chaosfirebolt.converter.api.initialization;

import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.initialization.source.SerializedArrayClassPathSource;
import com.github.chaosfirebolt.converter.api.initialization.transformation.RomanIntegerArrayToMapTransformation;

import java.util.Arrays;
import java.util.Map;

/**
 * Initialization source using provided serialized array.
 */
public class ProvidedInitializationData extends BaseInitializationData<RomanInteger[], Map<String, RomanInteger>> {

    private static final String PATH = "values-array.ser";

    /**
     * Constructs new instance providing required source and transformation.
     */
    public ProvidedInitializationData() {
        super(new SerializedArrayClassPathSource(PATH), new RomanIntegerArrayToMapTransformation());
    }

    @Override
    protected void doCleanup(RomanInteger[] input, Map<String, RomanInteger> output) {
        Arrays.fill(input, null);
        output.clear();
    }
}
