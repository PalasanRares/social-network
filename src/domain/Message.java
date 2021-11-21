package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Message extends Entity<Integer>{
    User sender;
    List<User> receivers;
    String message;
    LocalDate data;
    Message reply;

    public Message(User sender, List<User> receivers, String message, LocalDate data, Message reply) {
        this.sender = sender;
        this.receivers = receivers;
        this.message = message;
        this.data = data;
        this.reply = reply;
    }

    public Message(User sender, List<User> receivers, String message, LocalDate data) {
        this.sender = sender;
        this.receivers = receivers;
        this.message = message;
        this.data = data;
        this.reply=null;
    }
    public Message getReply(){
        return reply;
    }
    public List<Integer> getRIds(){
        List<Integer> l=new ArrayList<>();
        for(User u:getReceivers())
            l.add(u.getId());

        return l;

    }
    public void setReply(Message repl){
        this.reply=repl;
    }
    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", receivers=" + receivers +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
