package edu.java.scrapper.data.db;

import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

public interface CrudRepository<T, I> {
    Optional<T> add(T entity);

    Optional<T> removeById(I id);

    Optional<T> findById(I id);

    List<T> findAll();

//    /**
//     * Base CRUD operation. Create.
//     */
//    void create(T entity) throws DataIntegrityViolationException;
//
//    /**
//     * Base CRUD operation. Delete.
//     */
//    void delete(T entity);
//
//    /**
//     * Base CRUD operation. Update.
//     */
//    void update(T entity);
//
//    /**
//     * Base CRUD operation. Get.
//     */
//    T get(I entityId);
}
