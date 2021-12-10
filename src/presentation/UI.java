package presentation;

import application.Service;
import domain.FriendRequest;
import domain.Friendship;
import domain.Message;
import domain.User;
import domain.UserFriendDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Ui class for displaying console user interface
 */
public class UI {
    private final Service service;

    /**
     * Creates new object of type UI containing a reference to a object of type Service
     * @param service object of type Service
     */
    public UI(Service service) {
        this.service = service;
    }

    private void rel2UI(String args1,String args2){
        for (UserFriendDTO dto :  service.findRelationsByMonth(Integer.parseInt(args1),args2)) {
            System.out.println(dto);
        }
    }
    private void uiSendMessage(String[] copyOfRange) {
        if (service.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
        }else{
            service.addMessage(copyOfRange);
        }
    }
    private void uiConversatie(String u1,String u2){
        //List<Message> conv=service.Conversatie(Integer.parseInt(u1),Integer.parseInt(u2));
        for(Message m: service.Conversatie(Integer.parseInt(u1),Integer.parseInt(u2)))
            System.out.println(m);
    }
    private void uiEditMessage(String[] args){
        if (service.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
        }else{
            service.modifyMessage(Integer.parseInt(args[0]),args[1],Integer.parseInt(args[2]));
        }
    }
    private void uiRemoveMessage(String arg)
    {
        if (service.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
        }else{
            service.removeMessage(arg);
        }
    }
    private void addUI(String[] args) {
        switch (args[0]) {
            case "user" -> service.addUser(Arrays.copyOfRange(args, 1, args.length));
            case "friend" -> service.addFriendship(Arrays.copyOfRange(args, 1, args.length));
        }
    }

    private void rmUI(String[] args) {
        switch (args[0]) {
            case "user" -> service.removeUser(args[1]);
            case "friend" -> service.removeFriendship(args[1], args[2]);
        }
    }

    private void printUsersUI(Iterable<User> users) {
        for (User u : users) {
            System.out.println(u);
        }
    }

    private void printFriendshipsUI(Iterable<Friendship> friendships) {
        for (Friendship f : friendships) {
            System.out.println(f);
        }
    }

    private void printMessagesUI(Iterable<Message> messages) {
        for (Message m : messages) {
            System.out.println(m);
        }
    }
    private void lsUI(String[] args) {
        switch (args[0]) {
            case "users" -> printUsersUI(service.findAllUsers());
            case "friendships" -> printFriendshipsUI(service.findAllFriendships());
            case "messages" -> printMessagesUI(service.findAllMessages());
        }
    }

    private void connectionsUI(String[] args) {
        System.out.println(service.nrOfCommunities());
    }

    private void largestUI() {
        List<User> longestRoad = service.largestCommunity();
        if (longestRoad != null) {
            for (User u : longestRoad) {
                System.out.println(u);
            }
        }
    }

    private void showFriendships(String userId) {
        for (UserFriendDTO dto : service.findFriendsForUser(Integer.parseInt(userId))) {
            System.out.println(dto);
        }
    }
    private void uiHelp(){
        System.out.println("add user <prenume> <nume> <data-nasterii>");
        System.out.println("add friend <idUser1> <idUser2> <data optionala>");
        System.out.println("rm user <id>");
        System.out.println("rm friend <idUser1> <idUser2>");
        System.out.println("ls users");
        System.out.println("ls friendships");
        System.out.println("ls messages");
        System.out.println("connections (toate comunitatile)");
        System.out.println("largest (cel mai lung drum)");
        System.out.println("friendships <userId>(toti prietenii unui user)");
        System.out.println("friendshipsMonth <userId> <month>(toti prietenii unui user dupa o anumita luna)");
        System.out.println("login <userId>");
        System.out.println("sendRequest <userId> (trimite cerere catre userul specificat)");
        System.out.println("seeRequests (afiseaza toate cererile primite de userul curent)");
        System.out.println("acceptRequest <userId> (accepta cererea de la userul specificat)");
        System.out.println("rejectRequest <userId> (refuza cererea de la userul specificat)");
        System.out.println("sendMessage <ReceiverId1> ...<ReceiverIdN> <0> <Message>");
        System.out.println("removeMessage <MessageId>");
        System.out.println("editMessage <MessageId> <Message> <ReplyMsgId>");
        System.out.println("conversatie <UserId1> <UserId2>");
        System.out.println("exit");

    }

    private void uiLogin(String userId) {
        if (!service.loginUser(Integer.parseInt(userId))) {
            System.out.println("Ups, something went wrong with logging you in");
        }
        else {
            System.out.println("Logged in successfully");
        }
    }

    private void uiSendFriendRequest(String friendId) {
        if (service.getLoggedInUser() == null) {
            System.out.println("You are not logged in");
        }
        else {
            service.sendFriendRequest(Integer.parseInt(friendId));
        }
    }

    private void uiSeeFriendRequests() {
        if (service.getLoggedInUser() != null) {
            for (FriendRequest friendRequest : service.getFriendRequests()) {
                System.out.println(friendRequest);
            }
        }
        else {
            System.out.println("You are not logged in");
        }
    }

    private void uiAcceptRequest(String from) {
        service.acceptFriendRequest(Integer.parseInt(from));
    }

    private void uiRejectRequest(String from) {
        service.rejectFriendRequest(Integer.parseInt(from));
    }

    /**
     * Runs the console user interface
     */
    public void runUI() {
        Scanner scanner = new Scanner(System.in);
        boolean uiRunning = true;
        while (uiRunning) {
            System.out.print("~");
            String[] args = scanner.nextLine().split(" ");
            switch (args[0]) {
                case "exit" -> uiRunning = false;
                case "help" -> uiHelp();
                case "add" -> addUI(Arrays.copyOfRange(args, 1, args.length));
                case "rm" -> rmUI(Arrays.copyOfRange(args, 1, args.length));
                case "ls" -> lsUI(Arrays.copyOfRange(args, 1, args.length));
                case "connections" -> connectionsUI(Arrays.copyOfRange(args, 1, args.length));
                case "largest" -> largestUI();
                case "friendships" -> showFriendships(args[1]);
                case "friendshipsMonth" -> rel2UI(args[1],args[2]);
                case "login" -> uiLogin(args[1]);
                case "sendRequest" -> uiSendFriendRequest(args[1]);
                case "seeRequests" -> uiSeeFriendRequests();
                case "acceptRequest" -> uiAcceptRequest(args[1]);
                case "rejectRequest" -> uiRejectRequest(args[1]);
                case "sendMessage" -> uiSendMessage(Arrays.copyOfRange(args, 1, args.length));
                case "removeMessage" -> uiRemoveMessage(args[1]);
                case "editMessage" -> uiEditMessage(Arrays.copyOfRange(args, 1, args.length));
                case "conversatie" -> uiConversatie(args[1],args[2]);
            }
        }
    }



}
