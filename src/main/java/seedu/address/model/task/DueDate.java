package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.util.DateFormatUtil.isValidDateFormat;
import static seedu.address.model.util.DateFormatUtil.parseDate;

import java.util.Date;

/**
 * Represents a due date in the {@link Task}.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DUEDATE_CONSTRAINTS =
            "DueDate should only contain numbers, and it should be in one of the following formats:\n"
                    + " dd-mm-yy, dd-mm-yyyy, dd-mm-yy HHmm, dd-mm-yyyy HHmm\n"
                    + "Note: 24h time format";

    public final String value;
    public final Date valueDate;

    /**
     * Constructs a {@code DueDate}.
     *
     * @param dueDate A valid dueDate number.
     */
    public DueDate(String dueDate) {
        requireNonNull(dueDate);
        checkArgument(isValidDueDate(dueDate), MESSAGE_DUEDATE_CONSTRAINTS);
        value = dueDate;
        valueDate = parseDate(value);
    }

    /**
     * Returns true if a given string is a valid due date.
     *
     * @param test date to be checked
     */
    public static boolean isValidDueDate(String test) {
        requireNonNull(test);
        return isValidDateFormat(test);
    }

    /**
     * Returns true if a given {@code DueDate} is overdue.
     */
    public boolean isOverdue() {
        return new Date().compareTo(valueDate) > 0;
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
        return (int) valueDate.toInstant().toEpochMilli();
    }

}
