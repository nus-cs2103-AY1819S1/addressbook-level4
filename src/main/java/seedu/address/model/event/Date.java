package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents an Event's date in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Event dates can take in DD-MM-YYYY input, and should not be blank";

    /*
     * First character of DD must be 0-3
     * First character of MM must be 0 or 1
     * Regex not enough to check for valid dates. Need to use a SimpleDateFormat parser as well.
     */
    public static final String DATE_VALIDATION_REGEX = "[0-3]\\d-[01]\\d-\\d{4}";

    public final String value;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid Date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (!test.matches(DATE_VALIDATION_REGEX)) {
            return false;
        }

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        df.setLenient(false);

        try {
            df.parse(test);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * Returns true if current date falls on an earlier date or on the same date as the other date.
     */
    public boolean isLessThanOrEqualTo(Date otherDate) {
        if (otherDate == this) {
            return true;
        }

        String[] dateParts = this.toString().split("-");
        //parseInt ignores leading zeros like 01 or 09 when converting from String to int
        int year = Integer.parseInt(dateParts[2]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[0]);

        String[] otherDateParts = otherDate.toString().split("-");
        int otherYear = Integer.parseInt(otherDateParts[2]);
        int otherMonth = Integer.parseInt(otherDateParts[1]);
        int otherDay = Integer.parseInt(otherDateParts[0]);

        if (year > otherYear) { //start year is more than end year
            return false;
        } else if (year == otherYear && month > otherMonth) { //same year but start month is more than end month
            return false;
        } else if (year == otherYear && month == otherMonth && day > otherDay) { //same year, same month but start
                                                                                // day is more than end day
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
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
