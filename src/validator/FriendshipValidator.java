package validator;

import domain.Friendship;
import validator.exception.IdZeroException;
import validator.exception.ValidationException;

/**
 * Validates entity of type Friendship
 */
public class FriendshipValidator implements Validator<Friendship> {
    @Override
    public void validate(Friendship entity) throws ValidationException {
        if (entity.getId().getFirst() <= 0) {
            throw new IdZeroException("The first id cannot be less than one");
        }
        if (entity.getId().getSecond() <= 0) {
            throw new IdZeroException("The second id cannot be less than one");
        }
    }
}
