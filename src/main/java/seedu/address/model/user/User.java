package seedu.address.model.user;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.common.Name;
import seedu.address.model.common.Password;
import seedu.address.model.common.Username;

/**
 * Represents a User in the FoodZoom.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class User {

    // Identity fields
    private Name name;
    private final Username username;
    private final Password password;

    /**
     * Every field must be present and not null.
     */
    public User(Name name, Username username, Password password) {
        requireAllNonNull(name, username, password);
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public User(Username username, Password password) {
        requireAllNonNull(username, password);
        this.username = username;
        this.password = password;
    }

    public Name getName() {
        return name;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    /**
     * Returns true if both users of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameUser(User otherUser) {
        if (otherUser == this) {
            return true;
        }

        return otherUser != null
                && otherUser.getUsername().equals(getUsername());
    }

    /**
     * Returns true if both users of the same username and password.
     */
    public boolean isSameExistingUser(User otherUser) {
        if (otherUser == this) {
            return true;
        }

        return otherUser != null
                && otherUser.getUsername().equals(getUsername())
                && otherUser.getPassword().equals(getPassword());
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherPerson = (User) other;
        if (otherPerson.getName() == null) {
            return otherPerson.getUsername().equals(getUsername())
                    && otherPerson.getPassword().equals(getPassword());
        } else {
            return otherPerson.getName().equals(getName())
                    && otherPerson.getUsername().equals(getUsername())
                    && otherPerson.getPassword().equals(getPassword());
        }

    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        //To print for Login
        if (getName() == null) {
            builder.append("Username: ")
                    .append(getUsername());
        } else {
            builder.append(getName())
                    .append(" Username: ")
                    .append(getUsername())
                    .append(" Password: ")
                    .append(getPassword());
        }


        return builder.toString();
    }


}
