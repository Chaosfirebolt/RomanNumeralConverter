package com.github.chaosfirebolt.converter.api.initialization;

import java.io.InputStream;

/**
 * Source returning {@link InputStream}.
 * @since 3.2.0
 */
public abstract class InputStreamSource implements InputSource<InputStream> {

    private final String path;

    /**
     * @param path path to be used for creating the stream
     * @since 3.2.0
     */
    protected InputStreamSource(String path) {
        this.path = path;
    }

    @Override
    public final InputStream getInputData() {
        return this.createInputStream(this.path);
    }

    /**
     * Creates an input stream from provided path.
     * @param path path to be used for creating the stream
     * @return a new {@link InputStream}
     * @since 3.2.0
     */
    protected abstract InputStream createInputStream(String path);
}
