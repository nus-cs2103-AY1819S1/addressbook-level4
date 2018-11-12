package seedu.address.model.common;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's username in FoodZoom.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class Username {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Username should only contain alphanumeric characters, it should not be blank "
                    + "and be 3 to 64 characters long.";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}]{3,64}";
    public final String value;


    /**
     * Constructs a {@code Username}.
     *
     * @param username A valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        isValidUsername(username);
        value = username;
    }


    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && value.equals(((Username) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
