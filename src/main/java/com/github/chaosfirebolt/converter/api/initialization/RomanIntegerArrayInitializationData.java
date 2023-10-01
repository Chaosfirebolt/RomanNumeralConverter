package com.github.chaosfirebolt.converter.api.initialization;

import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.initialization.source.InputSource;
import com.github.chaosfirebolt.converter.api.initialization.transformation.RomanIntegerArrayToMapTransformation;

import java.util.Arrays;
import java.util.Map;

/**
 * InitializationSource from an array of roman integers.
 */
public class RomanIntegerArrayInitializationData extends BaseInitializationData<RomanInteger[], Map<String, RomanInteger>> {

    /**
     * Constructs a new instance with provided roman integer array source.
     * @param inputSource the array source
     */
    public RomanIntegerArrayInitializationData(InputSource<RomanInteger[]> inputSource) {
        super(inputSource, new RomanIntegerArrayToMapTransformation());
    }

    @Override
    protected void doCleanup(RomanInteger[] input, Map<String, RomanInteger> output) {
        Arrays.fill(input, null);
        output.clear();
    }
}
