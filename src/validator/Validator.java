package validator;

import validator.exception.ValidationException;

/**
 * Interface for generic validator
 * @param <EType> generic type of entity to be validated
 */
public interface Validator<EType> {
    /**
     * Validates the entity based on a set of rules
     * @param entity entity to be validated
     * @throws ValidationException if entity is not valid
     */
    void validate(EType entity) throws ValidationException;
}
