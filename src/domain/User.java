package domain;

import constants.DateFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * User class for defining a network user
 */
public class User extends Entity<Integer> {

    private String firstName;
    private String lastName;
    private LocalDate birthday;

    private List<User> friendsList = null;

    /**
     * Creates a new instance of the User class
     * @param id unique identifier for user
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param birthday birthday of the user
     */
    public User(String firstName, String lastName, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    /**
     * Add a friend to the user's friends list
     * @param friend friend to be added
     */
    public void addFriend(User friend) {
        if (friendsList == null) {
            friendsList = new ArrayList<>();
        }
        friendsList.add(friend);
    }

    /**
     * Removes a friend from the user's friends list
     * @param friend friend to be removed
     */
    public void removeFriend(User friend) {
        if (friendsList == null) {
            throw new NullPointerException("Friends list is empty");
        }
        friendsList.removeIf(user -> user.equals(friend));
    }

    @Override
    public String toString() {
        return super.toString() + " " + firstName + " " + lastName + " " + birthday.format(DateFormatter.STANDARD_DATE_FORMAT).toString();
    }

    /**
     * First name getter
     * @return user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Last name getter
     * @return user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Birthday getter
     * @return user's birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Friends list getter
     * @return user's friends list
     */
    public List<User> getFriendsList() {
        return friendsList;
    }
}
