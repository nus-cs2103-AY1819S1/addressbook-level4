package seedu.meeting.model.shared;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.commons.util.AppUtil.checkArgument;

// @@author Derek-Hardy
/**
 * Represents a description of the group.
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 *
 * {@author Derek-Hardy}
 */
public class Description {

    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Description can take any values, and it should not be blank.";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DESCRIPTION_VALIDATION_REGEX = "[^\\s].*";

    public final String statement;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_DESCRIPTION_CONSTRAINTS);
        statement = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return statement;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && statement.equals(((Description) other).statement)); // state check
    }

    @Override
    public int hashCode() {
        return statement.hashCode();
    }
}
