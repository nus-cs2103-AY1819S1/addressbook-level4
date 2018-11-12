package seedu.address.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a User's username.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class Username {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Usernames should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}]*";

    public final String username;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Username(String name) {
        requireNonNull(name);
        checkArgument(isValidUsername(name), MESSAGE_USERNAME_CONSTRAINTS);
        username = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Username)) {
            return false;
        }

        return username.equals(((Username) other).username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

}
