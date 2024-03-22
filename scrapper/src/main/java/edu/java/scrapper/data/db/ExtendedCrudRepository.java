package edu.java.scrapper.data.db;

public interface ExtendedCrudRepository<T, I> extends CrudRepository<T, I> {
    /**
     * Extended CRUD operation. Create or update entity.
     */
    void put(T entity);

    /**
     * Extended CRUD operation. Create or update and get created or updated entity.
     */
    T putAndReturn(T entity);

    /**
     * Extended CRUD operation. Create and get created entity.
     */
    T createAndReturn(T entity);

    /**
     * Extended CRUD operation. Update and get updated entity.
     */
    T updateAndReturn(T entity);
}
