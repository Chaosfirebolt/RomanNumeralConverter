package com.github.chaosfirebolt.converter.testUtil;

import java.lang.reflect.Field;

public class FieldAccessor {

    public static Object getValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            boolean revertToNotAccessible = false;
            if (!field.isAccessible()) {
                field.setAccessible(true);
                revertToNotAccessible = true;
            }
            Object value = field.get(object);
            if (revertToNotAccessible) {
                field.setAccessible(false);
            }
            return value;
        } catch (ReflectiveOperationException exc) {
            throw new RuntimeException("Pay attention", exc);
        }
    }
}