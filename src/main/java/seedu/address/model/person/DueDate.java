package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;

/**
 * Represents a Task's due date number in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS =
            "DueDate should only contain numbers, and it should be in one of the following formats:\n" +
                    " dd-mm-yy, dd-mm-yyyy, dd-mm-yy HHmm, dd-mm-yyyy HHmm";
    public static final String DUEDATE_FORMAT_MINIMAL = "dd-MM-yy";
    public static final String DUEDATE_FORMAT = "dd-MM-yy HHmm";
    public static final String DUEDATE_REGEX = "\\d{1,2}-\\d{1,2}-\\d{2,4}( \\d{4})?";
    public final String value;

    /**
     * Constructs a {@code DueDate}.
     *
     * @param dueDate A valid dueDate number.
     */
    public DueDate(String dueDate) {
        requireNonNull(dueDate);
        checkArgument(isValidDueDate(dueDate), MESSAGE_DUEDATE_CONSTRAINTS);
        value = dueDate;
    }

    /**
     * Returns true if a given string is a valid due date.
     * Checks if due date is either one of two possible formats.
     * {@link #isValidDueDateMinimalFormat(String)}.{@link #isValidDueDateStandardFormat(String)}
     */
    public static boolean isValidDueDate(String test) {
        if (test == null) {
            throw new NullPointerException();
        }
        if (!test.matches(DUEDATE_REGEX)) {
            return false;
        }
        return isValidDueDateMinimalFormat(test) || isValidDueDateStandardFormat(test);
    }

    /**
     * Returns true if a given string is a valid due date minimal format.
     * Minimal format specified in {@link #DUEDATE_FORMAT_MINIMAL}
     */
    private static boolean isValidDueDateMinimalFormat(String test) {
        try {
            SimpleDateFormat formatMinimal = new SimpleDateFormat(DUEDATE_FORMAT_MINIMAL);
            formatMinimal.setLenient(false);
            formatMinimal.parse(test);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given string is a valid due date standard format.
     * Standard format specified in {@link #DUEDATE_FORMAT}
     */
    private static boolean isValidDueDateStandardFormat(String test) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(DUEDATE_FORMAT);
            format.setLenient(false);
            format.parse(test);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                && value.equals(((DueDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
