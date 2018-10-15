package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Stores all the friendships among users using an adjacency list.
 * Friendships must be bilateral. If person A is a friend of person B, B is also a friend of A.
 *
 * @author agendazhang
 *
 */
public class Friends {

    public static final String MESSAGE_FRIENDS_CONSTRAINTS = "Friends should be identified by index";
    private static HashMap<Person, ArrayList<Person>> friendsAdjacencyList = new HashMap<>();

    public void addPersonAsKey(Person person) {
        friendsAdjacencyList.put(person, new ArrayList<>());
    }

    public boolean checkPersonExists(Person person) {
        return friendsAdjacencyList.containsKey(person);
    }

    public void addFriend(Person current, Person friend) {
        ArrayList<Person> currentList = friendsAdjacencyList.get(current);
        currentList.add(friend);
    }

    public ArrayList<Person> getFriends(Person person) {
        return friendsAdjacencyList.get(person);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        Set<Map.Entry<Person, ArrayList<Person>>> allFriendships = friendsAdjacencyList.entrySet();
        String friendshipTable = "";
        for (Map.Entry<Person, ArrayList<Person>> friendship : allFriendships) {
            friendshipTable += "User: ";
            Person currentUser = friendship.getKey();
            String currentUserName = currentUser.getName().toString();
            friendshipTable += currentUserName + ", Friends: ";
            ArrayList<Person> userFriends = friendship.getValue();
            ArrayList<String> userFriendsList = new ArrayList<>();
            for (Person p : userFriends) {
                userFriendsList.add(p.getName().toString());
            }
            friendshipTable += userFriendsList + "\n";
        }
    }
}
