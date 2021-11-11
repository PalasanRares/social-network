package domain;

import java.util.Objects;

/**
 * Generic class for holding two objects of different types
 * @param <Type1> generic type
 * @param <Type2> generic type
 */
public class Tuple<Type1, Type2> {
    private Type1 first;
    private Type2 second;

    /**
     * Creates a new instance of the class Tuple
     * @param first first element in the tuple
     * @param second second element in the tuple
     */
    public Tuple(Type1 first, Type2 second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Getter for the first element
     * @return first element in the tuple
     */
    public Type1 getFirst() {
        return first;
    }

    /**
     * Setter for the first element
     * @param first new first element value
     */
    public void setFirst(Type1 first) {
        this.first = first;
    }

    /**
     * Getter for the second element
     * @return second element in the tuple
     */
    public Type2 getSecond() {
        return second;
    }

    /**
     * Setter for the second element
     * @param second new second element value
     */
    public void setSecond(Type2 second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return (Objects.equals(first, tuple.first) && Objects.equals(second, tuple.second)) || (Objects.equals(first, tuple.second) &&
                Objects.equals(second, tuple.first));
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
