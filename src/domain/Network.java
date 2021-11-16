package domain;

import java.util.*;

/**
 * Network containing all the users
 */
public class Network {
    private List<User> userList;

    /**
     * Creates a new entity of type Network
     * @param size number of users in the network
     */
    public Network(int size) {
        this.userList = new ArrayList<>(size);
    }

    /**
     * Add users to the user list
     * @param u user to be added
     */
    public void addUser(User u) {
        userList.add(u);
    }

    private List<User> BFS(int source, int[] levels) {
        List<User> result = new LinkedList<>();
        result.add(userList.get(source));
        boolean[] visited = new boolean[userList.size()];
        LinkedList<Integer> queue = new LinkedList<>();
        visited[source] = true;
        levels[source] = 0;
        queue.add(source);
        while (queue.size() != 0) {
            source = queue.poll();
            List<User> friendsList = userList.get(source).getFriendsList();
            if (friendsList != null) {
                for (User n : friendsList) {
                    if (!visited[userList.indexOf(n)]) {
                        visited[userList.indexOf(n)] = true;
                        result.add(n);
                        levels[userList.indexOf(n)] = levels[source] + 1;
                        queue.add(userList.indexOf(n));
                    }
                }
            }
        }
        return result;
    }

    private int maxLevel(int[] levels) {
        int max = -1;
        for (int i : levels) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    /**
     * Returns a list of all member in the largest community in the network
     * @return list of users in the largest community
     */
    public List<User> largestSocialCommunity(Iterable<Friendship> connections) {
        addFriendships(connections);
        int longestRoad = 0;
        int correspondingIndex = -1;
        for (int i = 0; i < userList.size(); i++) {
            int[] levels = new int[userList.size()];
            BFS(i, levels);
            int max = maxLevel(levels);
            if (max > longestRoad) {
                longestRoad = max;
                correspondingIndex = i;
            }
        }
        if (correspondingIndex == -1) {
            return null;
        }
        return BFS(correspondingIndex, new int[userList.size()]);
    }

    private void DFSUtils(int v, boolean[] visited) {
        visited[v] = true;
        for (int x = 0; x < userList.size(); x++) {
            List<User> friendsList = userList.get(v).getFriendsList();
            if (friendsList != null) {
                if (userList.get(v).getFriendsList().contains(userList.get(x)) && !visited[x]) {
                    DFSUtils(x, visited);
                }
            }
        }
    }

    private void addFriendships(Iterable<Friendship> connections) {
        for (Friendship f : connections) {
            User u1 = null;
            User u2 = null;
            for (User u : userList) {
                if (u.getId().equals(f.getId().getFirst().getId())) {
                    u1 = u;
                }
                if (u.getId().equals(f.getId().getSecond().getId())) {
                    u2 = u;
                }
            }
            if (u1 != null && u2 != null) {
                u1.addFriend(u2);
                u2.addFriend(u1);
            }
        }
    }

    /**
     * Calculates the number of connected components in the network
     * @return number of connected components in the network
     */
    public int connectedComponents(Iterable<Friendship> connections) {
        addFriendships(connections);
        int nr = 0;
        boolean[] visited = new boolean[userList.size()];
        for (int v = 0; v < userList.size(); v++) {
            if (!visited[v]) {
                DFSUtils(v, visited);
                nr += 1;
            }
        }
        return nr;
    }
}
