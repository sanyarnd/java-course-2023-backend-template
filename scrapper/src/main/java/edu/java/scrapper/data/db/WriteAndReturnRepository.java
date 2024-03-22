package edu.java.scrapper.data.db;

import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

public interface WriteAndReturnRepository<T> {
    /**
     * Base CRUD operation. Create and get created entity.
     */
    T createAndReturn(T entity) throws DataIntegrityViolationException;

    /**
     * Base CRUD operation. Delete and get deleted entity.
     */
    Optional<T> deleteAndReturn(T entity);

    /**
     * Base CRUD operation. Update and get updated entity.
     */
    Optional<T> updateAndReturn(T entity);

    /**
     * Extended CRUD operation. Create or update and get created or updated entity.
     */
    T upsertAndReturn(T entity);
}
