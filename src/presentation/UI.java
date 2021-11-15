package presentation;

import application.Service;
import domain.Friendship;
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

    private void lsUI(String[] args) {
        switch (args[0]) {
            case "users" -> printUsersUI(service.findAllUsers());
            case "friendships" -> printFriendshipsUI(service.findAllFriendships());
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
                case "add" -> addUI(Arrays.copyOfRange(args, 1, args.length));
                case "rm" -> rmUI(Arrays.copyOfRange(args, 1, args.length));
                case "ls" -> lsUI(Arrays.copyOfRange(args, 1, args.length));
                case "connections" -> connectionsUI(Arrays.copyOfRange(args, 1, args.length));
                case "largest" -> largestUI();
                case "friendships" -> showFriendships(args[1]);
            }
        }
    }

}
