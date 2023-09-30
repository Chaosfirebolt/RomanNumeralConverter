package com.github.chaosfirebolt.converter.api.initialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

/**
 * Defines skeleton for reading an input stream and transforming the data to required format.
 * @param <StreamResult> the result after reading the stream
 * @param <TransformationResult> the result after transforming data from the stream
 */
public abstract class GenericInputStreamTransformation<StreamResult, TransformationResult> implements Transformation<InputStream, TransformationResult> {

    /**
     * Just making it protected, no need for public.
     */
    protected GenericInputStreamTransformation() {
    }

    /**
     * Reads and transforms the input stream to the required result. Closes the stream.
     * The default {@link IOException} handler will wrap any IOException in {@link UncheckedIOException} before rethrowing it.
     * @param input input data
     * @return transformed result
     * @throws UncheckedIOException on IOException failure
     * @since 3.2.0
     */
    @Override
    public final TransformationResult transform(InputStream input) {
        try (input) {
            StreamResult streamResult = this.readInputStream(input);
            return this.transformResultToMap(streamResult);
        } catch (IOException exc) {
            return this.handleIOException(exc);
        }
    }

    /**
     * Reads the input stream.
     * @param inputStream stream to read from
     * @return data from the stream
     * @throws IOException if reading from the stream fails
     * @since 3.2.0
     */
    protected abstract StreamResult readInputStream(InputStream inputStream) throws IOException;

    /**
     * Transforms data from the stream to required format
     * @param streamResult data read from the stream
     * @return transformed data
     * @since 3.2.0
     */
    protected abstract TransformationResult transformResultToMap(StreamResult streamResult);

    /**
     * Handles {@link IOException} thrown when dealing with the input stream.
     * This default implementation wraps the exception in an {@link UncheckedIOException}.
     * @param exc previously thrown IOException
     * @return backup transformation result.
     * @throws UncheckedIOException in case of IOException
     * @since 3.2.0
     */
    protected TransformationResult handleIOException(IOException exc) {
        throw new UncheckedIOException(exc);
    }
}
