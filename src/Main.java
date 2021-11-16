import application.Service;
import domain.Friendship;
import domain.Tuple;
import domain.User;
import presentation.UI;
import repository.Repository;
import repository.db.FriendshipDbRepository;
import repository.db.UserDbRepository;
public class Main {
    /**
     * main program
     * @param args console line arguments
     */
    public static void main(String[] args) {

        Repository<Integer, User> userRepository = new UserDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres", "Indiferent1");
        Repository<Tuple<User, User>, Friendship> friendshipRepository = new FriendshipDbRepository("jdbc:postgresql://localhost:5432/SocialNetwork", "postgres", "Indiferent1");



        Service service = new Service(friendshipRepository, userRepository);

       UI ui = new UI(service);
        ui.runUI();
    }
}
