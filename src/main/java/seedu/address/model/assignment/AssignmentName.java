package seedu.address.model.assignment;

import seedu.address.model.common.Validator;

/**
 * Represents an assignment's name in the app.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class AssignmentName extends Validator implements Comparable<AssignmentName> {

    public static final String MESSAGE_CONSTRAINTS =
            "Assignment name should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    /**
     * Constructs a {@code AssignmentName}.
     *
     * @param name A valid name.
     */
    public AssignmentName(String name) {
        super(name);
    }

    /**
     * Returns true if a given string matches the class VALIDATION_REGEX.
     */
    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int compareTo(AssignmentName other) {
        return this.getValue().compareTo(other.getValue());
    }

    @Override
    public String getValue() {
        return this.internalString;
    }

}
