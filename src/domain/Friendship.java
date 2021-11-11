package domain;

/**
 * Extends Entity and has id of type Tuple of Integer and Integer
 */
public class Friendship extends Entity<Tuple<Integer, Integer>> {

    /**
     * Creates a new instance of class Friendship
     * @param id unique identifier of the new instance
     */
    public Friendship(Tuple<Integer, Integer> id) {
        super(id);
    }

    @Override
    public String toString() {
        return id.getFirst() + " " + id.getSecond();
    }

}
