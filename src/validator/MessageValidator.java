package validator;

import domain.Message;
import domain.User;
import validator.exception.EmptyFieldException;
import validator.exception.ImpossibleAgeException;
import validator.exception.ValidationException;

import java.time.LocalDate;

public class MessageValidator implements Validator<Message>{
    @Override
    public void validate(Message entity) throws ValidationException {
        if (entity.getReceivers().size()==0) {
            throw new EmptyFieldException("The message must be sent to someone!");
        }
        if (entity.getMessage().equals("")) {
            throw new EmptyFieldException("Message cannot be empty");
        }
    }
}
