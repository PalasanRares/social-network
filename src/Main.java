import application.Service;
import domain.Friendship;
import domain.Message;
import domain.Tuple;
import domain.User;
import presentation.UI;
import repository.Repository;
import repository.db.FriendshipDbRepository;
import repository.db.MessagesDbRepository;
import repository.db.UserDbRepository;
public class Main {
    /**
     * main program
     * @param args console line arguments
     */
    public static void main(String[] args) {

        Repository<Integer, User> userRepository = new UserDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres", "postgres");
        Repository<Tuple<User, User>, Friendship> friendshipRepository = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres", "postgres");
        Repository<Integer, Message> msgRepository = new MessagesDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres", "postgres");


        Service service = new Service(friendshipRepository, userRepository, msgRepository);

       UI ui = new UI(service);
       ui.runUI();

//        System.out.println("printing msgs");
//        Repository<Integer, Message> msgRepository = new MessagesDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres", "postgres");
//        for(Message msg:msgRepository.findAll()){
//            System.out.println(msg);
//        }
//        System.out.println("done");
    }
}
