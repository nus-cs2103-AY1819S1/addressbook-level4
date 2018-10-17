package seedu.inventory.model.staff;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.commons.util.AppUtil.checkArgument;

/**
 * Represents a Staff's username in the system.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class Username {
    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Username should only contain alphanumberic characters only and it should not be blank.";

    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}]*$";

    public final String username;

    /**
     * Constructs a {@code Username}.
     *
     * @param username A valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);
        this.username = username;
    }

    public static boolean isValidUsername(String username) {
        return username.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.username;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Username)) {
            return false;
        }

        if (other == this) {
            return true;
        }

        return username.equals(((Username) other).username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
