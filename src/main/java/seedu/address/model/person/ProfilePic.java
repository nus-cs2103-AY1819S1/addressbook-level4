package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's profile picture path in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPath(String)}
 */
public class ProfilePic {

    public static final String MESSAGE_PROFILEPIC_CONSTRAINTS =
            "Profile picture should be sized 500x300";

    /*
     * The first character of the profile picture path must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PATH_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code ProfilePic}.
     *
     * @param path A valid profile picture path.
     */
    public ProfilePic(String path) {
        requireNonNull(path);
        checkArgument(isValidPath(path), MESSAGE_PROFILEPIC_CONSTRAINTS);
        this.value = path;
    }

    /**
     * Returns true if a given string is a valid path.
     */
    public static boolean isValidPath(String test) {
        return test.matches(PATH_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProfilePic // instanceof handles nulls
                && value.equals(((ProfilePic) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
