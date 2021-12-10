package application;

import constants.DateFormatter;
import constants.Sortbydate;
import domain.*;
import org.postgresql.util.PSQLException;
import repository.ConvRepository;
import repository.ModifiableRepository;
import repository.Repository;
import validator.exception.DuplicateFriendshipException;
import validator.exception.MessageNotFoundException;
import validator.exception.UserNotFoundException;
import validator.exception.ValidationException;


import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service which manages user and friendship repositories
 */
public class Service {
    private final Repository<Tuple<User, User>, Friendship> friendshipRepository;
    private final Repository<Integer, User> userRepository;
    private final ModifiableRepository<Tuple<User, User>, FriendRequest> friendRequestRepository;
    private User loggedInUser;
    private final ConvRepository<Integer, Message> messageRepository;
    /**
     * Creates an instance of type Services
     * @param friendshipRepository friendshipRepository to be used
     * @param userRepository userRepository to be used
     * @param messageRepository messageRepository to be used
     */
    public Service(Repository<Tuple<User, User>, Friendship> friendshipRepository, Repository<Integer, User> userRepository, ConvRepository<Integer, Message> messageRepository, ModifiableRepository<Tuple<User, User>, FriendRequest> friendRequestRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.friendRequestRepository = friendRequestRepository;
        loggedInUser = null;
    }

    /**
     * Adds a friendship connection between two users
     * @param args String array, contains first user id and second user id
     */
    public void addFriendship(String[] args) {
        try {
            User u1 = userRepository.findOne(Integer.parseInt(args[0]));
            User u2 = userRepository.findOne(Integer.parseInt(args[1]));
            if (u1 == null || u2 == null) {
                throw new UserNotFoundException("Could not find user id");
            }
            Friendship friendship = new Friendship(new Tuple<>(u1, u2));
            for (Friendship f : friendshipRepository.findAll()) {
                if (f.equals(friendship)) {
                    throw new DuplicateFriendshipException("The users are already friends");
                }
            }
            u1.addFriend(u2);
            u2.addFriend(u1);
            friendshipRepository.save(friendship);
        }
        catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Removes a friendship connection between two users if it exists
     * @param id1 id of the first user
     * @param id2 id of the second user
     */
    public void removeFriendship(String id1, String id2) {
        User user1 = userRepository.findOne(Integer.parseInt(id1));
        User user2 = userRepository.findOne(Integer.parseInt(id2));
        Tuple<User, User> id = new Tuple<>(user1, user2);
        if (friendshipRepository.findOne(id) != null) {
            friendshipRepository.remove(id);
        }
    }

    /**
     * Returns all the friendship connections from the repository
     * @return an iterable object containing all the friendships in repository
     */
    public Iterable<Friendship> findAllFriendships() {
        return friendshipRepository.findAll();
    }

    /**
     * Returns all the messages from the repository
     * @return an iterable object containing all the messages in repository
     */
    public Iterable<Message> findAllMessages() {
        return messageRepository.findAll();
    }
    /**
     * Return the largest connected component of the friendship network
     * @return the number of users in the largest component
     */
    public int nrOfCommunities() {
        Network network = new Network(userRepository.size());
        for (User user : userRepository.findAll()) {
            network.addUser(user);
        }
        return network.connectedComponents(friendshipRepository.findAll());
    }

    /**
     * Return the users which form the deepest tree in the friendship network
     * @return list containing all the users in the deepest tree
     */
    public List<User> largestCommunity() {
        Network network = new Network(userRepository.size());
        for (User user : userRepository.findAll()) {
            network.addUser(user);
        }
        return network.largestSocialCommunity(friendshipRepository.findAll());
    }

    /**
     * Creates and adds a new user to the user repository
     * @param args attributes of the new user
     */
    public void addUser(String[] args) {
        User user = new User(args[0], args[1], LocalDate.parse(args[2], DateFormatter.STANDARD_DATE_FORMAT));
        userRepository.save(user);
    }
    /**
     * Creates and adds a new message to the message repository
     * @param args attributes of the new message
     */
    public void addMessage(String[] args){

        //User sender = userRepository.findOne(Integer.parseInt(args[0]));
        User sender =userRepository.findOne(getLoggedInUser().getId());
        List<User> list = new ArrayList<>();
        int i=0;

            while (Integer.parseInt(args[i]) != 0) {
                User r = userRepository.findOne(Integer.parseInt(args[i]));
                if (r != null)
                    list.add(r);
                i++;
            }

        i++;
        Message msg = new Message(sender,list,args[i],LocalDate.now(),null);
        messageRepository.save(msg);
    }
    public  Iterable<Message> Conversatie(Integer u1,Integer u2){
        if(u1 == null || u2 == null)throw new NullPointerException("id must not be null");
        User user1 = userRepository.findOne(u1);
        User user2 = userRepository.findOne(u2);
        if(user1 == null || user2 == null )throw new UserNotFoundException("user does not exist");

        List<Message> ar=messageRepository.conversatie(u1,u2);
        Collections.sort(ar, new Sortbydate());
        return ar;
    }
    /**
     * Removes a message based on his id
     * @param id id of the message to be removed
     */
    public void removeMessage(String id) {
        try {
            Message msg = messageRepository.findOne(Integer.parseInt(id));
            if (msg == null) {
                throw new MessageNotFoundException("Could not find message id");
            }
            Iterable<Message> messages = messageRepository.findAll();
            if (messages != null) {
                for (Message m : messages) {
                    if (m.getReply()!=null && m.getReply().getId()!=null)
                        if(m.getReply().getId().equals(Integer.parseInt(id))) {
                        //update reply sa fie null pt ca nu mai exista mesajul
                            Message ms= new Message(null,null,m.getMessage(),null,null);
                            ms.setId(m.getId());
                            messageRepository.modify(ms);
                    }
                }
            }
            messageRepository.remove(Integer.parseInt(id));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }

    }
    public void modifyMessage(Integer id,String msg,Integer reply){
        if(id == null)throw new NullPointerException("id must not be null");
        Message m = messageRepository.findOne(id);
        if(m == null)throw new MessageNotFoundException("message not found");
        try {
            Message ms= new Message(null,null,msg,null,messageRepository.findOne(reply));
            ms.setId(id);
            messageRepository.modify(ms);

        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void removeUser(String id) {
        try {
            User user = userRepository.findOne(Integer.parseInt(id));
            if (user == null) {
                throw new UserNotFoundException("Could not find user id");
            }
            Iterable<Friendship> friendships = friendshipRepository.findAll();
            if (friendships != null) {
                for (Friendship friendship : friendships) {
                    if (friendship.getId().getFirst().getId().equals(user.getId()) || friendship.getId().getSecond().getId().equals(user.getId())) {
                        friendshipRepository.remove(friendship.getId());
                    }
                }
            }
            userRepository.remove(Integer.parseInt(id));
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Returns an iterable object containing all the users in the repository
     * @return iterable object containing all the users
     */
    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }


    public Iterable<UserFriendDTO> findFriendsForUser(Integer userId) {
        return StreamSupport.stream(findAllFriendships().spliterator(), false)
                .filter(friendship -> friendship.getId().getFirst().getId().equals(userId) || friendship.getId().getSecond().getId().equals(userId))
                .map(friendship -> {
                    User friend;
                    if (friendship.getId().getFirst().getId().equals(userId)) {
                        friend = friendship.getId().getSecond();
                    }
                    else {
                        friend = friendship.getId().getFirst();
                    }
                    return new UserFriendDTO(friend.getFirstName(), friend.getLastName(), friendship.getDate());
                })
                .collect(Collectors.toList());
    }

    public Iterable<UserFriendDTO> findRelationsByMonth(int user, String mon) {
     return StreamSupport.stream(findAllFriendships().spliterator(),false)
             .filter(friendship->(friendship.getId().getFirst().getId().equals(user)|| friendship.getId().getSecond().getId().equals(user))&&friendship.getDate().getMonth().toString().equals(mon))
             .map(friendship->{
                 User friend;
                 if(friendship.getId().getFirst().equals(userRepository.findOne(user))){
                    friend = friendship.getId().getSecond();
                 }
                 else{
                     friend = friendship.getId().getFirst();
                 }
                 return new UserFriendDTO(friend.getFirstName(),friend.getLastName(),friendship.getDate());
             })
             .collect(Collectors.toList());
    }

    public boolean loginUser(Integer userId) {
        User foundUser = userRepository.findOne(userId);
        if (foundUser == null) {
            return false;
        }
        loggedInUser = foundUser;
        return true;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void sendFriendRequest(Integer friendId) {
        try {
            User friend = userRepository.findOne(friendId);
            if (friend == null) {
                throw new UserNotFoundException("User was not found");
            }
            FriendRequest friendRequest = new FriendRequest(new Tuple<>(loggedInUser, friend), "pending");
            if (friendRequestRepository.findOne(friendRequest.getId()) != null) {
                throw new DuplicateFriendshipException("This friend request already exists");
            }
            friendRequestRepository.save(friendRequest);
        }
        catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Iterable<FriendRequest> getFriendRequests() {
        return friendRequestRepository.findAllForId(new Tuple<>(new User(), loggedInUser));
    }

    public void acceptFriendRequest(Integer from) {
        try {
            User friend = userRepository.findOne(from);
            if (friend == null) {
                throw new UserNotFoundException("User was not found");
            }
            Tuple<User, User> requestId = new Tuple<>(friend, loggedInUser);
            if (!friendRequestRepository.findOne(requestId).getStatus().equals("pending")) {
                throw new ValidationException("Cannot accept request");
            }
            FriendRequest toAccept = new FriendRequest(requestId, "accepted");
            if (!friendRequestRepository.modify(toAccept)) {
                throw new ValidationException("Request could not be accepted");
            }
            else {
                friendshipRepository.save(new Friendship(toAccept.getId()));
            }
        }
        catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void rejectFriendRequest(Integer from) {
        try {
            User friend = userRepository.findOne(from);
            if (friend == null) {
                throw new UserNotFoundException("User was not found");
            }
            Tuple<User, User> requestId = new Tuple<>(friend, loggedInUser);
            if (!friendRequestRepository.findOne(requestId).getStatus().equals("pending")) {
                throw new ValidationException("Cannot reject request");
            }
            FriendRequest toReject = new FriendRequest(requestId, "rejected");
            if (!friendRequestRepository.modify(toReject)) {
                throw new ValidationException("Request could not be rejected");
            }
        }
        catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
