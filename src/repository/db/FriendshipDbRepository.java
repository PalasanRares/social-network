package repository.db;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.Repository;
import validator.FriendshipValidator;
import validator.UserValidator;
import validator.Validator;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class FriendshipDbRepository implements Repository<Tuple<Integer, Integer>, Friendship> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Friendship> validator;

    public FriendshipDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = new FriendshipValidator();
    }

    @Override
    public void save(Friendship entity) {
        String sql = "INSERT INTO \"Friendships\" (\"FirstUserId\", \"SecondUserId\", \"FriendshipDate\") VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getId().getFirst());
            ps.setInt(2, entity.getId().getSecond());
            ps.setDate(3, Date.valueOf(entity.getDate()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Tuple<Integer, Integer> id) {
        String sql = "DELETE FROM \"Friendships\" WHERE \"FirstUserId\" = ? AND \"SecondUserId\" = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id.getFirst());
            ps.setInt(2, id.getSecond());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();;
        }
    }

    @Override
    public Friendship findOne(Tuple<Integer, Integer> id) {
        Friendship friendship = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Friendships\" WHERE \"FirstUserId\" = ? AND \"SecondUserId\" = ?")) {
            ps.setInt(1, id.getFirst());
            ps.setInt(2, id.getSecond());
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("FirstUserId");
                Integer id2 = resultSet.getInt("SecondUserId");
                LocalDate friendshipDate = resultSet.getDate("FriendshipDate").toLocalDate();
                friendship = new Friendship(new Tuple<>(id1, id2), friendshipDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendship;
    }

    @Override
    public Iterable<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"Friendships\"");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id1 = resultSet.getInt("FirstUserId");
                Integer id2 = resultSet.getInt("SecondUserId");
                LocalDate friendshipDate = resultSet.getDate("FriendshipDate").toLocalDate();
                Friendship friendship = new Friendship(new Tuple<>(id1, id2), friendshipDate);
                friendships.add(friendship);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }

    @Override
    public int size() {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS Size FROM \"Friendships\"");
             ResultSet resultSet = ps.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("Size");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
