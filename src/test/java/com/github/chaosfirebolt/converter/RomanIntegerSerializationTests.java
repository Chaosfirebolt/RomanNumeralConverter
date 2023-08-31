package com.github.chaosfirebolt.converter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class RomanIntegerSerializationTests {

    private static Stream<Arguments> data() {
        return Stream.of(Arguments.of(RomanInteger.ONE), Arguments.of(RomanInteger.THOUSAND), Arguments.of(RomanInteger.parse("111")),
                        Arguments.of(RomanInteger.parse("1111")), Arguments.of(RomanInteger.parse("3691")), Arguments.of(RomanInteger.parse("2759")),
                        Arguments.of(RomanInteger.parse("917")));
    }

    @ParameterizedTest
    @MethodSource("data")
    public void testSerializable(RomanInteger romanInteger) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(romanInteger);
        objectOutputStream.flush();
        objectOutputStream.close();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        RomanInteger deserialized = (RomanInteger) objectInputStream.readObject();
        objectInputStream.close();

        assertNotSame(romanInteger, deserialized, "Expected different instances, but was same");
        assertEquals(romanInteger.getArabic(), deserialized.getArabic(), "Arabic representation not as expected");
        assertEquals(romanInteger.getRoman(), deserialized.getRoman(), "Roman representation not as expected");
    }
}
