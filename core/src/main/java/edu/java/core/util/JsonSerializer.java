package edu.java.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonSerializer<T> {
    String serialize(T entity) throws JsonProcessingException;

    T deserialize(String from) throws JsonProcessingException;
}
