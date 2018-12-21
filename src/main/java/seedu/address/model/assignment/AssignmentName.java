package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an assignment's name in the app.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class AssignmentName implements Comparable<AssignmentName> {

    public static final String MESSAGE_CONSTRAINTS =
            "Assignment name should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String internalString;

    /**
     * Constructs a {@code AssignmentName}.
     *
     * @param name A valid name.
     */
    public AssignmentName(String name) {
        requireNonNull(name);
        checkArgument(isValid(name), MESSAGE_CONSTRAINTS);
        internalString = name;
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

    public String getValue() {
        return internalString;
    }

    @Override
    public String toString() {
        return internalString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignmentName // instanceof handles nulls
                && internalString.equals(((AssignmentName) other).internalString)); // state check
    }
    @Override
    public int hashCode() {
        return internalString.hashCode();
    }

}
