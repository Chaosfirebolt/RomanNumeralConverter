package com.github.chaosfirebolt.converter.api.initialization.transformation;

/**
 * Represents a transformation from input to output
 * @param <I> input type
 * @param <O> result type
 */
@FunctionalInterface
public interface Transformation<I, O> {

    /**
     * Transforms the input data to required result.
     * @param input input data
     * @return transformed data
     */
    O transform(I input);
}
