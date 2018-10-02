package seedu.address.model.user;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a User in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class User {
    // Identity fields
    private final Username username;
    private final Password password;
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final List<Friendship> friendRequests = new ArrayList<>();
    private final List<Friendship> friends = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public User(Username username, Password password, Name name, Phone phone, Email email) {
        requireAllNonNull(username, password, name, phone, email);
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns true if both users of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two users.
     */
    public boolean isSameUser(User otherUser) {
        if (otherUser == this) {
            return true;
        }

        return otherUser != null
                && otherUser.getName().equals(getName())
                && (otherUser.getPhone().equals(getPhone()) || otherUser.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both users have the same identity and data fields.
     * This defines a stronger notion of equality between two users.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;
        return otherUser.getUsername().equals(getUsername());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password, name, phone, email);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername())
                .append(" Password: ")
                .append(getPassword())
                .append(" Name: ")
                .append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail());
        /*.append(" Friends: ");
        getTags().forEach(builder::append);*/
        return builder.toString();
    }

    /**
     * Allows a user to add other users as friends.
     * Friendship request will not be created if friendship request already exists.
     * Friendship request will not be created if friendship already exists.
     * @param user User is not able to add oneself as a friend.
     */
    public void addFriend(User user) {
        // checks to make sure that friend is not oneself
        if (!user.isSameUser(this)) {
            Friendship friendship = new Friendship(this, this);
            // checks that friendship is not already in friendRequests
            if (!user.friendRequests.contains(friendship) && !user.friends.contains(friendship)) {
                user.friendRequests.add(friendship);
            }
        }
    }

    /**
     * @return String of all the user's friends separated by newline character.
     */
    public String listFriends() {
        return listHelper(friends);
    }

    /**
     * @return String that contains all the friendRequests received of this user separated by newline character.
     */
    public String listFriendRequests() {
        return listHelper(friendRequests);
    }

    /**
     * Helper method.
     * @param list List that you want to print out.
     * @return String that contains all elements in list.
     */
    public String listHelper(List<Friendship> list) {
        String toReturn = "";
        for (Friendship friendship: list) {
            toReturn += friendship.getFriendUser().getName() + "\n";
        }
        return toReturn;
    }

    /**
     * Ensures that the friendship can only be accepted by the party who did not initiate the request.
     * Changes friendship status to ACCEPTED.
     * Deletes friendship from friendRequests for the accepting party.
     * Adds friendship to friends list for both parties.
     * @param username Name of the friend to accept.
     */
    public void acceptFriendRequest(Name username) {
        Friendship friendship = findFriendshipInList(friendRequests, username);
        User friend = friendship.getFriendUser();
        // ensures that the RESTAURANT who initiated the friendship is not the one accepting
        if (!friendship.getInitiatedBy().equals(this)) {
            // changes friendship to accepted
            friendship.changeFriendshipStatus();

            // deletes from friendRequests for both parties
            friendRequests.remove(friendship);

            // adds to friends for both parties
            friends.add(friendship);
            Friendship friendship2 = new Friendship(this, friend, FriendshipStatus.ACCEPTED);
            friend.friends.add(friendship2);
        }
    }

    /**
     * Deletes the friendship request of the user with name username
     * @param username Name of the user that you want to delete friendRequest of
     */
    public void deleteFriendRequest(Name username) {
        Friendship friendship = findFriendshipInList(friendRequests, username);
        friendRequests.remove(friendship);
    }

    /**
     * Deletes the friendship for both parties when delete is initiated by one party.
     * Deletes friendship only if friendship exists.
     * @param username Name of the friend you want to delete
     */
    public void deleteFriend(Name username) {
        // gets the friendship for both parties
        Friendship friendship = findFriendshipInList(friends, username);
        User friend = friendship.getFriendUser();
        Friendship friendship2 = findFriendshipInList(friend.friends, this.getName());

        // deletes the friendship for both parties
        if (friends.contains(friendship)) {
            friends.remove(friendship);
            friend.friends.remove(friendship2);
        }
    }

    /**
     * Helper method to find friendship in list.
     * @param list List in which you would like to search in.
     * @param username Name of the friend that you would like to find.
     * @return Friendship between this user and user with Name username.
     */
    public Friendship findFriendshipInList(List<Friendship> list, Name username) {
        for (Friendship friendship: list) {
            if (friendship.getFriendUser().getName().equals(username)) {
                return friendship;
            }
        }
        return null;
    }
}
