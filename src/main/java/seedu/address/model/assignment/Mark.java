package seedu.address.model.assignment;

import seedu.address.model.common.Validator;

/**
 * Represents an assignment's mark in the app.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Mark extends Validator implements Comparable<Mark> {

    public static final String MESSAGE_CONSTRAINTS =
            "Mark should be a real number";
    public static final String VALIDATION_REGEX = "^\\d+(\\.\\d+)?$";

    /**
     * Constructs a {@code Mark}.
     *
     * @param mark A valid real number.
     */
    public Mark(String mark) {
        super(mark);
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


    @Override
    public Float getValue() {
        return Float.valueOf(this.internalString);
    }

}
