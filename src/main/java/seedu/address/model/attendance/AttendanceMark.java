package seedu.address.model.attendance;

import seedu.address.model.common.Mark;

/**
 * Represents an attendance mark in the app.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class AttendanceMark extends Mark {

    public static final String MESSAGE_CONSTRAINTS =
            "AttendanceMark should be a 0 or a 1";

    /**
     * Constructs a {@code AttendanceMark}.
     *
     * @param mark A valid real number.
     */
    public AttendanceMark(String mark) {
        super(mark);
    }

    /**
     * Returns true if a given string has an int value of 0 or 1.
     */
    public static boolean isValid(String test) {
        int value = Integer.parseInt(test);
        if (value == 1 || value == 0) {
            return true;
        }
        return false;
    }
}
