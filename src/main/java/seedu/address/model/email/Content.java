package seedu.address.model.email;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


//@@author EatOrBeEaten
/**
 * Represents content in Email.
 * Guarantees: immutable; is valid as declared in {@link #isValidContent(String)}
 */
public class Content {

    public static final String MESSAGE_CONTENT_CONSTRAINTS =
            "Content can take any values, and it should not be blank";

    /*
     * The first character of the content must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CONTENT_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Subject}.
     *
     * @param content A valid subject.
     */
    public Content(String content) {
        requireNonNull(content);
        checkArgument(isValidContent(content), MESSAGE_CONTENT_CONSTRAINTS);
        value = content;
    }

    /**
     * Returns true if a given string is a valid content.
     */
    public static boolean isValidContent(String test) {
        return test.matches(CONTENT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Content // instanceof handles nulls
                && value.equals(((Content) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
