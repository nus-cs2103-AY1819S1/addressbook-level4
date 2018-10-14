package seedu.address.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Name;
import seedu.address.model.user.Password;
import seedu.address.model.user.User;
import seedu.address.model.user.Username;

/**
 * Wraps all data for User Data.
 */
public class UserData {

    private HashMap<Username, User> usernameUserHashMap;
    private List<Jio> jios;

    public UserData() {
        usernameUserHashMap = new HashMap<>();
        jios = new ArrayList<>();
    }

    public HashMap<Username, User> getUsernameUserHashMap() {
        return usernameUserHashMap;
    }

    public List<Jio> getJios() {
        return jios;
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

    public void removeUser(User user) {
        usernameUserHashMap.remove(user.getUsername());
    }

    // Jios methods
    public boolean hasJioName(Name jioName) {
        return jios.stream().anyMatch(jio -> jio.getName().equals(jioName));
    }

    public boolean hasJio(Jio j) {
        return jios.stream().anyMatch(jio -> jio.equals(j));
    }

    public void addJio(Jio jio) {
        jios.add(jio);
    }

    public void removeJioOfName(Name jioName) {
        jios.removeIf(jio -> jio.getName().equals(jioName));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof UserData)) {
            return false;
        }
        UserData otherUserData = (UserData) other;
        return other == this // short circuit if same object
                || usernameUserHashMap.equals(otherUserData.getUsernameUserHashMap());
    }

    @Override
    public int hashCode() {
        return usernameUserHashMap.hashCode();
    }

}
