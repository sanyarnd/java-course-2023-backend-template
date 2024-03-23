package edu.java.scrapper.data.db;

import java.util.List;
import java.util.Optional;

public interface ReadRepository<T, I> {
    /**
     * Base CRUD operation. Get.
     */
    Optional<T> get(I entityId);

    /**
     * Base CRUD operation. Get all.
     */
    List<T> getAll();
}
