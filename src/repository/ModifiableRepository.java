package repository;

import domain.Entity;

public interface ModifiableRepository<ID, EType extends Entity<ID>> extends Repository<ID, EType> {

    void modify(EType newEntity);

    Iterable<EType> findAllForOne(ID id);
}
