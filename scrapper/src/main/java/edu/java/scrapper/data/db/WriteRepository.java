package edu.java.scrapper.data.db;

import org.springframework.dao.DataIntegrityViolationException;

public interface WriteRepository <T> {
    /**
     * Base CRUD operation. Create.
     */
    void create(T entity) throws DataIntegrityViolationException;

    /**
     * Base CRUD operation. Delete.
     */
    void delete(T entity);

    /**
     * Base CRUD operation. Update.
     */
    void update(T entity);

    /**
     * Extended CRUD operation. Create or update entity.
     */
    void upsert(T entity);
}
