package seedu.address.model.attendance;

import seedu.address.model.common.Validator;

/**
 * Represents a Lesson Name in the app.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Session extends Validator implements Comparable<Session> {

    public static final String MESSAGE_CONSTRAINTS =
            "Attendance name should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    /**
     * Constructs a {@code AssignmentName}.
     *
     * @param name A valid name.
     */
    public Session(String name) {
        super(name);
    }

    /**
     * Returns true if a given string matches the class VALIDATION_REGEX.
     */
    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int compareTo(Session other) {
        return this.getValue().compareTo(other.getValue());
    }

    @Override
    public String getValue() {
        return this.internalString;
    }

}
