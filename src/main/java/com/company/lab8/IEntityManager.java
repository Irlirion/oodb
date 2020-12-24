package com.company.lab8;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface IEntityManager<I extends Number> {
    /**
     * Method for saving entity to DB
     *
     * @param entity entity object
     */
    void persist(Entity<I> entity);

    /**
     * Method for updating the database record of this entity
     *
     * @param entity entity object
     * @return updated entity object
     */
    Entity<Long> merge(Entity<Long> entity);

    /**
     * Method for deleting the database record of entity
     *
     * @param entity entity that you want to delete
     */
    void remove(Entity<I> entity);

    /**
     * Method for getting all entities from table
     *
     * @return List of entities
     */
    List<Entity<I>> findAll(Class<?> entityClass);

    /**
     * Method for getting an entity from database
     *
     * @param id          id of entity record in database
     * @param entityClass class object of entity class
     * @return found entity, null if not found
     */
    Entity<Long> find(Class<?> entityClass, I id);

    /**
     * Refresh the object fields from database
     *
     * @param entity entity
     */
    void refresh(Entity<I> entity);

    void setTables(HashMap<String, HashSet<String>> tables);
}
