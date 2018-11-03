package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's username in OASIS.
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class Username {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
        "Username should only contain alphanumeric characters and spaces, the first character cannot be a "
            + "whitespace, it should not be blank, it should be unique among everyone in the system and it cannot be "
            + "\"Admin\"";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String username;

    /**
     * Constructs a {@code Username}.
     *
     * @param name A valid username.
     */
    public Username(String name) {
        requireNonNull(name);
        checkArgument(isValidUsername(name), MESSAGE_USERNAME_CONSTRAINTS);
        username = name;
    }

    /**
     * Returns true if a given string is a valid username.
     * Note: Does not verify constraint for unique among everyone in the system, neither does it check for Admin (if
     * it did, the Admin user couldn't be assigned a Username)
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
        return other == this // short circuit if same object
            || (other instanceof Username // instanceof handles nulls
            && username.equals(((Username) other).username)); // state check
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

}
