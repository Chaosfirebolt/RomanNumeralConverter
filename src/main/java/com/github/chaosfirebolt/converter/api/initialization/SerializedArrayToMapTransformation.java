package com.github.chaosfirebolt.converter.api.initialization;

import com.github.chaosfirebolt.converter.RomanInteger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Map;

/**
 * Deserializes a roman integer array and transforms it to a map.
 */
//TODO repackage transformations, sources, etc.
public class SerializedArrayToMapTransformation extends GenericInputStreamTransformation<RomanInteger[], Map<String, RomanInteger>> {

    private final Transformation<RomanInteger[], Map<String, RomanInteger>> delegate;

    /**
     * Creates a new instance, using provided delegate to do the transformation.
     * @param delegate delegate, responsible for the actual transformation from array to map
     */
    public SerializedArrayToMapTransformation(Transformation<RomanInteger[], Map<String, RomanInteger>> delegate) {
        this.delegate = delegate;
    }

    /**
     * Utility constructor, using {@link RomanIntegerArrayToMapTransformation} as the delegate.
     */
    public SerializedArrayToMapTransformation() {
        this(new RomanIntegerArrayToMapTransformation());
    }

    @Override
    protected RomanInteger[] readInputStream(InputStream inputStream) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        try {
            //TODO refactor!!! move this to input stream source
            return (RomanInteger[]) objectInputStream.readObject();
        } catch (ClassNotFoundException exc) {
            //TODO consider - rethrow as IO, or as custom unchecked exception
            throw new IOException("Error during object deserialization", exc);
        }
    }

    @Override
    protected Map<String, RomanInteger> transformResultToMap(RomanInteger[] romanIntegers) {
        return this.delegate.transform(romanIntegers);
    }
}
