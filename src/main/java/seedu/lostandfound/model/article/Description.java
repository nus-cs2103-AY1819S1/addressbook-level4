package seedu.lostandfound.model.article;

import static java.util.Objects.requireNonNull;
import static seedu.lostandfound.commons.util.AppUtil.checkArgument;

/**
 * Represents a Article's description in the article list.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Descriptions can take any values, and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValid(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
