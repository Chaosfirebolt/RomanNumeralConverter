package com.github.chaosfirebolt.converter.api.initialization;

import com.github.chaosfirebolt.converter.api.initialization.source.InputSource;
import com.github.chaosfirebolt.converter.api.initialization.transformation.Transformation;

/**
 * Provides the skeleton for {@link InitializationData} implementations.
 *
 * @param <I> input source type
 * @param <O> result type
 */
public non-sealed abstract class BaseInitializationData<I, O> implements InitializationData<O> {

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
   *
   * @param inputSource    a data source
   * @param transformation transformation from input to type required for initialization
   */
  protected BaseInitializationData(InputSource<I> inputSource, Transformation<I, O> transformation) {
    this.inputSource = inputSource;
    this.transformation = transformation;
  }

  @Override
  public final O getData() {
    input = inputSource.getInputData();
    output = transformation.transform(input);
    return output;
  }

  @Override
  public final void cleanup() {
    doCleanup(input, output);
  }

  /**
   * Does the actual cleanup, if necessary.
   *
   * @param input  the input to be cleaned up
   * @param output the result to be cleaned up
   */
  protected abstract void doCleanup(I input, O output);
}
