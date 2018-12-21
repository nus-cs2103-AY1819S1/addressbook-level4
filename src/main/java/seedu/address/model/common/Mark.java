package seedu.address.model.common;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an assignment's mark in the app.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Mark implements Comparable<Mark> {

    public static final String MESSAGE_CONSTRAINTS =
            "Mark should be a real number";
    public static final String VALIDATION_REGEX = "^(-+)?\\d+(\\.\\d+)?$";

    public final String internalString;

    /**
     * Constructs a {@code Mark}.
     *
     * @param mark A valid real number.
     */
    public Mark(String mark) {
        requireNonNull(mark);
        checkArgument(isValid(mark), MESSAGE_CONSTRAINTS);
        internalString = mark;
    }

    /**
     * Returns true if a given string matches the class VALIDATION_REGEX.
     */
    public static boolean isValid(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int compareTo(Mark other) {
        return this.getValue().compareTo(other.getValue());
    }

    public Double getValue() {
        return Double.valueOf(this.internalString);
    }

    @Override
    public String toString() {
        return this.internalString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mark // instanceof handles nulls
                && internalString.equals(((Mark) other).internalString)); // state check
    }
    @Override
    public int hashCode() {
        return internalString.hashCode();
    }

}
