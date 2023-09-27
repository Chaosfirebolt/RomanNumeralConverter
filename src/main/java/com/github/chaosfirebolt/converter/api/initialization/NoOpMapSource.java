package com.github.chaosfirebolt.converter.api.initialization;

import java.util.Map;

/**
 * No op implementation, returning empty map.
 * @param <K> type of map key
 * @param <V> type of map value
 */
public class NoOpMapSource<K, V> extends BaseInitializationSource<Void, Map<K, V>> {

    /**
     * Constructs new instance, with predefined input source and transformation to match no operation.
     */
    public NoOpMapSource() {
        super(() -> null, input -> Map.of());
    }

    @Override
    protected void doCleanup(Void input, Map<K, V> output) {
    }
}
