package domain;

import java.util.Objects;

public class FriendRequest extends Entity<Tuple<User, User>> {
    private String status;

    public FriendRequest(Tuple<User, User> id, String status) {
        super(id);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "From: " + id.getFirst() + " | To: " + id.getSecond() + " | Status: " + status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FriendRequest request = (FriendRequest) o;
        return Objects.equals(status, request.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }
}
