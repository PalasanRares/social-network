package repository.db;

import domain.FriendRequest;
import domain.Tuple;
import domain.User;
import repository.ModifiableRepository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class FriendRequestDbRepository implements ModifiableRepository<Tuple<User, User>, FriendRequest> {
    private final String url;
    private final String username;
    private final String password;

    public FriendRequestDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void save(FriendRequest entity) {
        String sql = "INSERT INTO friend_requests (\"from\", \"to\", status) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getId().getFirst().getId());
            ps.setInt(2, entity.getId().getSecond().getId());
            ps.setString(3, entity.getStatus());
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void remove(Tuple<User, User> requestId) {
        String sql = "DELETE FROM friend_requests WHERE \"from\" = ? AND \"to\" = ? OR \"to\" = ? AND \"from\" = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requestId.getFirst().getId());
            ps.setInt(2, requestId.getSecond().getId());
            ps.setInt(3, requestId.getSecond().getId());
            ps.setInt(4, requestId.getFirst().getId());
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public FriendRequest findOne(Tuple<User, User> userUserTuple) {
        return null;
    }

    @Override
    public Iterable<FriendRequest> findAll() {
        return null;
    }

    @Override
    public Iterable<FriendRequest> findAllForOne(Tuple<User, User> requestId) {
        Set<FriendRequest> requests = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friend_requests WHERE \"from\" = ? AND \"to\" = ? OR \"to\" = ? AND \"from\" = ?")) {
            statement.setInt(1, requestId.getFirst().getId());
            statement.setInt(2, requestId.getSecond().getId());
            statement.setInt(3, requestId.getSecond().getId());
            statement.setInt(4, requestId.getFirst().getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Integer from = resultSet.getInt("from");
                Integer to = resultSet.getInt("to");
                String status = resultSet.getString("status");
                PreparedStatement psUsers = connection.prepareStatement("SELECT * FROM \"Users\" WHERE \"UserId\" = ? OR \"UserId\" = ?");
                psUsers.setInt(1, from);
                psUsers.setInt(2, to);
                ResultSet users = psUsers.executeQuery();
                users.next();
                User user1 = new User(users.getString("FirstName"), users.getString("LastName"), users.getDate("Birthday").toLocalDate());
                user1.setId(users.getInt("UserId"));
                users.next();
                User user2 = new User(users.getString("FirstName"), users.getString("LastName"), users.getDate("Birthday").toLocalDate());
                user2.setId(users.getInt("UserId"));

                FriendRequest friendRequest = new FriendRequest(new Tuple<User, User>(user1, user2), status);
                requests.add(friendRequest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public int size() {
        return -1;
    }

    @Override
    public void modify(FriendRequest newRequest) {
        String sql = "UPDATE friend_requests SET status = ? WHERE \"from\" = ? AND \"to\" = ? OR \"to\" = ? AND \"from\" = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newRequest.getStatus());
            ps.setInt(2, newRequest.getId().getFirst().getId());
            ps.setInt(3, newRequest.getId().getSecond().getId());
            ps.setInt(4, newRequest.getId().getSecond().getId());
            ps.setInt(5, newRequest.getId().getFirst().getId());
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
