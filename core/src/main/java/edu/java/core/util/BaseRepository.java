package edu.java.core.util;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, I> {
    Optional<T> add(T entity);

    Optional<T> removeById(I id);

    Optional<T> findById(I id);

    List<T> findAll();
}
