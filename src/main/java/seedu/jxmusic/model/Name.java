package seedu.jxmusic.model;

import static java.util.Objects.requireNonNull;
import static seedu.jxmusic.commons.util.AppUtil.checkArgument;

/**
 * Represents a Playlist's name in the jxmusic book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the jxmusic must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String nameString;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_NAME_CONSTRAINTS);
        // checkArgument(isValidName(name), name + MESSAGE_NAME_CONSTRAINTS);
        nameString = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return nameString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && nameString.toLowerCase().equals(((Name) other).nameString.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return nameString.hashCode();
    }

}
