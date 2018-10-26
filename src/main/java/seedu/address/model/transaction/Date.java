package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author ericyjw
/**
 * Represents a Transaction Entry Date in the budget book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 * @author ericyjw
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
        "Transaction Date should only contain digits and dots, and it should not be blank";

    /*
     * The first character of the Date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_VALIDATION_REGEX = "[0-9]{1,2}[.]\\d{1,2}[.]\\d{4}";

    private String date;

    public Date() {
        this.date = null;
    }

    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }

    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    public static boolean isValidDate(Entry e) {
        String date = e.getDate().date;
        return date.matches(DATE_VALIDATION_REGEX);
    }

    public String getDate() {
        return this.date;
    }

    /**
     * Update the date by replacing the existing date with the new date
     * @param newDate new date to be updated
     */
    public void updateDate(String newDate) {
        this.date = newDate;
    }

    /**
     * Returns true if both dates are the same.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return otherDate.date.equals(date);
    }

    @Override
    public String toString() {
        return date;
    }
}
