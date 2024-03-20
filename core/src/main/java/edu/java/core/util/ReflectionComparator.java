package edu.java.core.util;

import edu.java.core.response.github.GithubPersistenceData;
import java.lang.reflect.Field;
import java.util.*;

public class ReflectionComparator {
    public static void main(String[] args) throws IllegalAccessException {
        var data1 = new GithubPersistenceData(1, 2, null, 4, 5);
        var data2 = new GithubPersistenceData(1, 2, 1, 4, 5);
        System.out.println(getDifference(data1, data2));
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
