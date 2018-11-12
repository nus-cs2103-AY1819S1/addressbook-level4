package seedu.address.model.group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Name;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Group class for users to create and join groups.
 * No two groups can have the same name.
 * Groups can only be joined by invitation only unlike jios.
 */
public class Group {
    private Name groupName;
    private List<User> acceptedUsers;
    private List<User> pendingUsers;
    private List<Username> pendingUsernames;

    /**
     * Creates a group with name groupName
     * Creator of the group is automatically in the list of accepted users.
     * @param groupName
     * @param creator
     */
    public Group(Name groupName, User creator) {
        this.groupName = groupName;
        this.acceptedUsers = new ArrayList<>();
        acceptedUsers.add(creator);
        this.pendingUsers = new ArrayList<>();
    }

    /**
     * Creates a group with name groupName
     * Initialises list of pendingUsers and acceptedUsers respectively
     * @param groupName
     * @param acceptedUsers
     * @param pendingUsers
     */
    public Group(Name groupName, List<User> acceptedUsers, List<User> pendingUsers) {
        this.groupName = groupName;
        this.acceptedUsers = acceptedUsers;
        this.pendingUsers = pendingUsers;
    }

    /**
     * Creates a group with groupName
     * Initialises pendingUsernames to the list of pending usernames.
     * @param groupName
     * @param pendingUsers
     */
    public Group(Name groupName, List<Username> pendingUsers) {
        this.groupName = groupName;
        this.pendingUsernames = pendingUsers;
    }

    /**
     * Adds creator to the list of accepted users.
     * @param user
     */
    public void addCreator(User user) {
        this.acceptedUsers.add(user);
    }

    /**
     * Returns list of usernames of all the pending users.
     * @return
     */
    public List<Username> getPendingUsernames() {
        return pendingUsernames;
    }

    /**
     * Allows users to add members into the group after creation of group
     * @param users
     */
    public void addMembers(User... users) {
        List<User> toAdd = Arrays.asList(users);
        toAdd.forEach(user -> {
            pendingUsers.add(user);
            user.addGroupRequest(this);
        });
    }

    /**
     * Allows users to add members into the group after creation of group
     * @param listUsers
     */
    public void addMembers(List<User> listUsers) {
        listUsers.forEach(user -> {
            pendingUsers.add(user);
            user.addGroupRequest(this);
        });
    }

    /**
     * Gets the group name
     * @return groupName
     */
    public Name getGroupName() {
        return groupName;
    }

    /**
     * Gets the list of Users who have accepted the invitation to join the group
     * @return acceptedUsers
     */
    public List<User> getAcceptedUsers() {
        return acceptedUsers;
    }

    /**
     * Gets the list of Users who have yet to accept the invitation to join the group
     * @return pendingUsers
     */
    public List<User> getPendingUsers() {
        return pendingUsers;
    }

    /**
     * Removes the User from the pendingUsers list.
     * Adds the User to the acceptedUsers list.
     * @param user User
     */
    public void changeMemberStatus(User user) {
        pendingUsers.remove(user);
        acceptedUsers.add(user);
    }

    /**
     * Removes the User from the pendingUsers list.
     * @param user User
     */
    public void removePendingUser(User user) {
        pendingUsers.remove(user);
    }

    /**
     * Removes the user from list of accepted users.
     * @param user User
     */
    public void removeAcceptedUser(User user) {
        acceptedUsers.remove(user);
    }

    public String toString() {
        return this.groupName.toString();
    }
    /**
     * Overrides the equals method
     * @param other Object
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof Group)) {
            return false;
        }
        Group otherGroup = (Group) other;
        return otherGroup.groupName.equals(groupName);
    }
}
