package domain;

import constants.DateFormatter;

import java.time.LocalDate;

/**
 * Extends Entity and has id of type Tuple of Integer and Integer
 */
public class Friendship extends Entity<Tuple<Integer, Integer>> {

    LocalDate friendshipDate ;

    /**
     * Creates a new instance of class Friendship
     * @param id unique identifier of the new instance
     */
    public Friendship(Tuple<Integer, Integer> id, LocalDate friendshipDate) {
        super(id);
        this.friendshipDate = friendshipDate;
    }

    public Friendship(Tuple<Integer, Integer> id) {
        super(id);
        this.friendshipDate = LocalDate.now();
    }

    @Override
    public String toString() {
        return id.getFirst() + " " + id.getSecond() + " " + friendshipDate.format(DateFormatter.STANDARD_DATE_FORMAT);
    }

    public LocalDate getDate() {
        return friendshipDate;
    }

}
