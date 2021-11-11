package repository;

import domain.Entity;

/**
 * Repository interface which stores items of type EType with id type ID
 * @param <ID> generic id type
 * @param <EType> generic type which extends Entity with id of type ID
 */
public interface Repository<ID, EType extends Entity<ID>> {
    /**
     * Saves entity to the repository
     * @param entity entity to be saved
     */
    void save(EType entity);

    /**
     * Removes entity associated with id value from the repository
     * @param id id of entity to be removed
     */
    void remove(ID id);

    /**
     * Finds entity associated with id value and returns it
     * @param id id of entity to be found
     * @return entity associated with id
     */
    EType findOne(ID id);

    /**
     * Return all entities from the repository
     * @return entities from repository
     */
    Iterable<EType> findAll();

    /**
     * Returns Number of elements in the repository
     * @return number of elements in the repository
     */
    int size();
}
