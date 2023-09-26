package com.github.chaosfirebolt.converter.api.initialization;

/**
 * Provides the skeleton for {@link InitializationSource} implementations.
 * @param <I> input source type
 * @param <O> result type
 */
public non-sealed abstract class BaseInitializationSource<I, O> implements InitializationSource<O> {

    /**
     * The source of data.
     */
    private final InputSource<I> inputSource;
    /**
     * Transforms the input data into the type required for initialization purposes.
     */
    private final Transformation<I, O> transformation;
    /**
     * The input from {@link #inputSource}.
     */
    private I input;
    /**
     * The result after applying the {@link #transformation}.
     */
    private O output;

    /**
     * Constructs a new instance with provided {@link InputSource} and {@link Transformation}.
     * @param inputSource a data source
     * @param transformation transformation from input to type required for initialization
     */
    protected BaseInitializationSource(InputSource<I> inputSource, Transformation<I, O> transformation) {
        this.inputSource = inputSource;
        this.transformation = transformation;
    }

    @Override
    public final O getSource() {
        this.input = this.inputSource.getInputData();
        this.output = this.transformation.transform(this.input);
        return this.output;
    }

    @Override
    public final void cleanup() {
        this.doCleanup(this.input, this.output);
    }

    /**
     * Does the actual cleanup, if necessary.
     * @param input the input to be cleaned up
     * @param output the result to be cleaned up
     */
    protected abstract void doCleanup(I input, O output);
}
