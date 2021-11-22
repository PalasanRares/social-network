package repository;

import domain.Entity;
import domain.Message;

import java.util.List;

public interface ConvRepository<ID, EType extends Entity<ID>> extends ModifiableRepository<ID, EType> {
    List<Message> conversatie(Integer u1 , Integer u2);
}
