package repository.memory;

import domain.Entity;
import domain.Friendship;
import repository.Repository;
import validator.Validator;
import validator.exception.ValidationException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * In memory repository which stores data inside memory
 * @param <ID> generic id type
 * @param <EType> generic type which holds id of type ID
 */
public abstract class InMemoryRepository<ID, EType extends Entity<ID>> implements Repository<ID, EType> {
    private Map<ID, EType> map;

    private Validator<EType> validator;

    /**
     * Takes as argument a Validator which will validate all the data saved to the repository
     * @param validator validator specific to the class stored in the repository
     */
    public InMemoryRepository(Validator<EType> validator) {
        this.validator = validator;
        map = new HashMap<>();
    }

    @Override
    public void save(EType entity) {
        if (entity == null) {
            throw new NullPointerException("Entity must not be null");
        }
        validator.validate(entity);
        if (map.get(entity.getId()) != null) {
            throw new ValidationException("Entity already in repository");
        }
        map.put(entity.getId(), entity);
    }

    @Override
    public void remove(ID id) {
        if (id == null) {
            throw new NullPointerException("ID must not be null");
        }
        map.remove(id);
    }

    @Override
    public EType findOne(ID id) {
        if (id == null) {
            throw new NullPointerException("ID must not be null");
        }
        return map.get(id);
    }

    @Override
    public Iterable<EType> findAll() {
        return map.values();
    }

    @Override
    public int size() {
        return map.size();
    }

    /**
     * Removes all entities from the repository
     */
    protected void clear() {
        map.clear();
    }
}
