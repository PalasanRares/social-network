package domain;

import java.util.Objects;

/**
 * Generic entity with attribute id of type ID
 * @param <ID> generic id
 */
public class Entity<ID> {
    /**
     * Unique identifier of the entity
     */
    protected ID id;

    /**
     * Creates an entity with the provided id
     * @param id id of the new entity
     */
    public Entity(ID id) {
        this.id = id;
    }

    public Entity() {};

    /**
     * Id getter
     * @return entity id
     */
    public ID getId() {
        return id;
    }

    /**
     * Id setter
     * @param id new id of entity
     */
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
