package seedu.address.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.jio.Jio;
import seedu.address.model.jio.UniqueJioList;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Wraps all data for User Data.
 */
public class UserData {

    private HashMap<Username, User> usernameUserHashMap;
    private HashMap<Name, Group> groupHashMap;
    private UniqueJioList jios;

    public UserData() {
        usernameUserHashMap = new HashMap<>();
        groupHashMap = new HashMap<>();
        jios = new UniqueJioList();
    }

    public UserData(UserData data) {
        this.usernameUserHashMap = data.usernameUserHashMap;
        this.groupHashMap = data.groupHashMap;
        this.jios = data.jios;

    }

    public HashMap<Username, User> getUsernameUserHashMap() {
        return usernameUserHashMap;
    }

    public HashMap<Name, Group> getGroupHashmap() {
        return groupHashMap;
    }

    public ObservableList<Group> getGroups() {
        return FXCollections.observableArrayList(new ArrayList<>(groupHashMap.values()));
    }

    public ObservableList<Jio> getJios() {
        return jios.asUnmodifiableObservableList();
    }

    public boolean hasUser(Username username) {
        return usernameUserHashMap.containsKey(username);
    }

    /**
     * Returns true if the {@code Password} matches that of the user with {@code Username}.
     */
    public boolean verifyLogin(Username username, Password password) {
        User userToCheck = usernameUserHashMap.get(username);
        return userToCheck.getPassword().equals(password);
    }

    public void addUser(User user) {
        usernameUserHashMap.put(user.getUsername(), user);
    }

    public void addUser(Username username, User user) {
        usernameUserHashMap.put(username, user);
    }

    public User getUser(Username username) {
        return usernameUserHashMap.get(username);
    }

    public List<User> getUsers() {
        return new ArrayList<User>(usernameUserHashMap.values());
    }
    //=========== Friend methods ============================================================================
    public boolean hasGroup(Name groupName) {
        return groupHashMap.containsKey(groupName);
    }

    public void addGroup(Group group) {
        groupHashMap.put(group.getGroupName(), group);
    }

    //=========== Jio methods ===============================================================================
    public boolean hasJioName(Name jioName) {
        return jios.asUnmodifiableObservableList().stream().anyMatch(jio -> jio.getName().equals(jioName));
    }

    public boolean hasJio(Jio j) {
        return jios.asUnmodifiableObservableList().stream().anyMatch(jio -> jio.equals(j));
    }

    public void addJio(Jio jio) {
        jios.add(jio);
    }

    /**
     * Removes jio with the specified name.
     * @param jioName name of jio
     */
    public void removeJioOfName(Name jioName) {
        //jios.removeIf(jio -> jio.getName().equals(jioName));
        Jio toRemove = new Jio();
        Iterator<Jio> iterator = jios.iterator();
        while (iterator.hasNext()) {
            Jio jio = iterator.next();
            if (jio.getName().equals(jioName)) {
                toRemove = jio;
                break;
            }
        }
        jios.remove(toRemove);
    }

    /**
     * Check if the user is in the jio.
     */
    public boolean isUserInJioOfName(Name jioName, User user) {
        return jios.asUnmodifiableObservableList().stream().anyMatch(jio
            -> (jio.getName().equals(jioName) && jio.hasUser(user)));
    }

    /**
     * Adds user to the specified jio. Assumes check for the existence of jio already done.
     */
    public void addUserToJioOfName(Name jioName, User user) {
        jios.asUnmodifiableObservableList().stream().forEach(jio -> {
            if (jio.getName().equals(jioName)) {
                jio.addUser(user);
            }
        });
    }

    /**
     * Checks if the specified user is the creator of the jio with the specified name.
     * @param jioName name of jio to be checked
     * @param user current user
     * @return True if the user is the creator of jio, false otherwise.
     */
    public boolean isCreatorOfJio(Name jioName, User user) {
        return jios.asUnmodifiableObservableList().stream().anyMatch(
            jio -> (jio.getName().equals(jioName) && jio.getCreator().equals(user.getUsername())));
    }

    /**
     * Clears all jios in userdata.
     */
    public void clearJio() {
        jios = new UniqueJioList();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof UserData)) {
            return false;
        }
        UserData otherUserData = (UserData) other;
        boolean[] equivalent = {true};
        usernameUserHashMap.forEach((k, v) -> {
            if (!v.equals(otherUserData.getUser(k))) {
                equivalent[0] = false;
            }
        });
        return other == this // short circuit if same object
                || equivalent[0];
    }

    @Override
    public int hashCode() {
        return usernameUserHashMap.hashCode();
    }
}
