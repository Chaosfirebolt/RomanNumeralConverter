package com.github.chaosfirebolt.converter.api.initialization;

import com.github.chaosfirebolt.converter.RomanInteger;
import com.github.chaosfirebolt.converter.api.initialization.source.RangeInputSource;
import com.github.chaosfirebolt.converter.api.initialization.source.SerializedArrayClassPathSource;
import com.github.chaosfirebolt.converter.api.initialization.transformation.RomanIntegerArrayToMapTransformation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InitializationDataTests {

    private  Map<String, RomanInteger> initializationData;
    private MockedConstruction<RomanIntegerArrayToMapTransformation> mockedTransformation;
    private RomanInteger[] resource;

    @BeforeEach
    public void setup() {
        this.initializationData = new HashMap<>(Map.of(RomanInteger.ONE.getRoman(), RomanInteger.ONE));
        this.mockedTransformation = Mockito.mockConstructionWithAnswer(RomanIntegerArrayToMapTransformation.class, invocation -> this.initializationData);
        this.resource = new RomanInteger[]{ RomanInteger.ONE };
    }

    @AfterEach
    public void cleanup() {
        this.mockedTransformation.close();
    }

    @Test
    public void providedInitializationData_AssertResourcesAreFreed() {
        try (MockedConstruction<SerializedArrayClassPathSource> ignored = Mockito.mockConstructionWithAnswer(SerializedArrayClassPathSource.class, invocation -> this.resource)) {
            ProvidedInitializationData providedInitializationData = new ProvidedInitializationData();
            executeAndAssert(providedInitializationData);
        }
    }

    private void executeAndAssert(BaseInitializationData<RomanInteger[], Map<String, RomanInteger>> initializationData) {
        initializationData.getData();
        initializationData.cleanup();
        assertArrayEquals(new RomanInteger[] { null }, this.resource, "Source result not cleaned");
        assertTrue(this.initializationData.isEmpty(), "Transformation result not cleaned");
    }

    @Test
    public void rangeInitializationData_AssertResourcesAreFreed() {
        try (MockedConstruction<RangeInputSource> ignored = Mockito.mockConstructionWithAnswer(RangeInputSource.class, invocation -> this.resource)) {
            RangeInitializationData rangeInitializationData = new RangeInitializationData(1, 1);
            executeAndAssert(rangeInitializationData);
        }
    }

    @Test
    public void romanIntegerArrayInitializationData_AssertResourcesAreFreed() {
        RomanIntegerArrayInitializationData data = new RomanIntegerArrayInitializationData(() -> this.resource);
        executeAndAssert(data);
    }
}
