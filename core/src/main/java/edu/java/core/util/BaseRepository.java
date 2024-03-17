package edu.java.core.util;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {
    Optional<T> add(T entity);

    Optional<T> removeById(ID id);

    Optional<T> findById(ID id);

    List<T> findAll();
}
