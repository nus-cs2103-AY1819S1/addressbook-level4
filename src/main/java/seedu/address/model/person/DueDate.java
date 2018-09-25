package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Task's due date number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate {


    public static final String MESSAGE_DUEDATE_CONSTRAINTS =
            "DueDate numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String DUEDATE_VALIDATION_REGEX = "\\d{3,}";
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
     */
    public static boolean isValidDueDate(String test) {
        return test.matches(DUEDATE_VALIDATION_REGEX);
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
