package repository.file;

import constants.DateFormatter;
import domain.Friendship;
import domain.User;
import validator.UserValidator;

import java.time.LocalDate;

/**
 * User repository which stores User objects inside files and memory
 */
public class UserInFileRepository extends InFileRepository<Integer, User> {
    /**
     * Creates an instance of type UserInFileRepository
     * @param fileName file from which data will be read
     */
    public UserInFileRepository(String fileName) {
        super(new UserValidator(), fileName);
    }

    @Override
    protected String createEntityAsString(User entity) {
        return entity.getId() + "," + entity.getFirstName() + "," + entity.getLastName() + "," + entity.getBirthday().format(DateFormatter.STANDARD_DATE_FORMAT).toString();
    }

    @Override
    protected User createEntityFromString(String string) {
        String[] attributes = string.split(",");
        Integer id = Integer.parseInt(attributes[0]);
        String firstName = attributes[1];
        String lastName = attributes[2];
        LocalDate birthday = LocalDate.parse(attributes[3], DateFormatter.STANDARD_DATE_FORMAT);
        User u = new User(firstName, lastName, birthday);
        u.setId(id);
        return u;
    }

    public Integer nextId() {
        Integer max = 0;
        for (User user : findAll()) {
            if (user.getId().compareTo(max) > 0) {
                max = user.getId();
            }
        }
        return max + 1;
    }
}
