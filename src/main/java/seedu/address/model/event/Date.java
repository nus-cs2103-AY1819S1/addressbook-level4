package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents an Event's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should be in the format YYYY-MM-DD, and it should not be blank";

    /**
     * A blank string " " is considered invalid.
     * Format must be in YYYY-MM-DD, using only - as the separator.
     */
    public static final String DATE_VALIDATION_REGEX = "^[0-9]{4}-" +
            "(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|" +
            "(02-(0[1-9]|[1-2][0-9]))|" +
            "((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";

    public final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = LocalDate.parse(date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public boolean isValidDate(String test) throws DateTimeParseException {
        if (!test.matches(DATE_VALIDATION_REGEX)) {
            return false;
        }

        // includes checks for leap years
        try {
            LocalDate.parse(test);
        } catch (DateTimeParseException) {
            return false;
        }
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
