package validator;

import domain.User;
import validator.exception.EmptyFieldException;
import validator.exception.IdZeroException;
import validator.exception.ImpossibleAgeException;
import validator.exception.ValidationException;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

/**
 * Validates all the user's attributes
 */
public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.getFirstName().equals("")) {
            throw new EmptyFieldException("First name cannot be empty");
        }
        if (entity.getLastName().equals("")) {
            throw new EmptyFieldException("Last name cannot be empty");
        }
        if (entity.getBirthday().isBefore(LocalDate.parse("1900-01-01"))) {
            throw new ImpossibleAgeException("That person cannot be alive anymore");
        }
    }
}
