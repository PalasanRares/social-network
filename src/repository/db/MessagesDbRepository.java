package repository.db;

import domain.Message;
import domain.User;
import repository.Repository;
import validator.MessageValidator;
import validator.Validator;
import validator.exception.ValidationException;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class MessagesDbRepository implements Repository<Integer, Message> {
    private final String url;
    private final String username;
    private final String password;
    private final Validator<Message> validator;
    public MessagesDbRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = new MessageValidator();
    }

    @Override
    public void save(Message entity) {
        try {
            validator.validate(entity);
        } catch (ValidationException ex) {
            System.out.println(ex.getMessage());
            return;
        }
        String sql = "INSERT INTO \"Messages\" (\"SenderId\", \"ReceiversIds\", \"Message\", \"SendingDate\", \"ReplyTo\") VALUES (?, ?, ? ,? ,?)";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getSender().getId());
            Array array = connection.createArrayOf("int", entity.getRIds().toArray());
            ps.setArray(2, array);
            ps.setString(3, entity.getMessage());
            ps.setDate(4, Date.valueOf(entity.getData()));
            ps.setInt(5,entity.getReply().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Integer integer) {
        String sql = "DELETE FROM \"Messages\" WHERE \"Id\" = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, integer);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();;
        }
    }

    @Override
    public Message findOne(Integer integer) {
        Message message = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Messages\" WHERE \"Id\" = ?")) {
            ps.setInt(1, integer);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Integer sid = resultSet.getInt("SenderId");
                Array rec= resultSet.getArray("ReceiversIds");
                String msg = resultSet.getString("Message");
                LocalDate day = resultSet.getDate("SendingDate").toLocalDate();
                Integer reply = resultSet.getInt("ReplyTo");

               // PreparedStatement psUser = connection.prepareStatement("SELECT * FROM \"Users\" WHERE \"UserId\" = ?");
                //    psUser.setInt(1, sid);
                //    ResultSet resUser = psUser.executeQuery();
                //String firstName = resUser.getString("FirstName");
               // String lastName = resUser.getString("LastName");
                //LocalDate birthday = resUser.getDate("Birthday").toLocalDate();
               // User sender = new User(firstName, lastName, birthday);
               // sender.setId(sid);

                List<User> receivers = getReceiversList(rec);
                message = new Message(getSender(sid),receivers,msg,day,findOne(reply));
                message.setId(integer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }
    public User getSender(Integer id){
        User user = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Users\" WHERE \"UserId\" = ?")) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                LocalDate birthday = resultSet.getDate("Birthday").toLocalDate();
                user = new User(firstName, lastName, birthday);
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }
    public List<User> getReceiversList(Array rec){
        List<User> rez =new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"Users\" WHERE \"UserId\" = ?")) {
            Integer[] reclist = (Integer[])rec.getArray();
            for( Integer id: reclist){
                ps.setInt(1, id);
                ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                LocalDate birthday = resultSet.getDate("Birthday").toLocalDate();
                User us = new User(firstName, lastName, birthday);
                us.setId(id);
                rez.add(us);
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez;
    }

    @Override
    public Iterable<Message> findAll() {
        Set<Message> messages = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from \"Messages\"");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("Id");
                Integer sid = resultSet.getInt("SenderId");
                Array rec= resultSet.getArray("ReceiversIds");
                String msg = resultSet.getString("Message");
                LocalDate day = resultSet.getDate("SendingDate").toLocalDate();
                Integer reply = resultSet.getInt("ReplyTo");

               // PreparedStatement psUser = connection.prepareStatement("SELECT * FROM \"Users\" WHERE \"UserId\" = ?");
                //psUser.setInt(1, sid);
                //ResultSet resUser = psUser.executeQuery();
                //String firstName = resUser.getString("FirstName");
               // String lastName = resUser.getString("LastName");
               // LocalDate birthday = resUser.getDate("Birthday").toLocalDate();
                //User sender = new User(firstName, lastName, birthday);
               // sender.setId(sid);

                List<User> receivers = getReceiversList(rec);
                Message message = new Message(getSender(sid),receivers,msg,day,findOne(reply));
                message.setId(id);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public int size() {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS Size FROM \"Messages\"");
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                Integer s = resultSet.getInt("Size");
                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
