package edu.java.core.util;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;


public class ReflectionComparator {
    private ReflectionComparator() {
    }

    public static Map<String, String> getObjectAsMap(Object object) throws IllegalAccessException {
        SortedMap<String, String> dictionary = new TreeMap<>();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            field.setAccessible(true);
            dictionary.put(fieldName, String.valueOf(field.get(object)));
        }
        return dictionary;
    }

    public static List<String> getDifference(Object object1, Object object2) throws IllegalAccessException {
        if (object1 == null || object2 == null) {
            return List.of();
        }
        Map<String, String> fieldsMap1 = getObjectAsMap(object1);
        Map<String, String> fieldsMap2 = getObjectAsMap(object2);
        return fieldsMap1.keySet().stream().map(
                        key -> {
                            String value1 = fieldsMap1.get(key);
                            String value2 = fieldsMap2.get(key);
                            if (value1 == null || !value1.equals(value2)) {
                                return String.format("[%s]: %s -> %s", key, value1, value2);
                            }
                            return null;
                        }
                ).filter(Objects::nonNull)
                .toList();
    }
}
