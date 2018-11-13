package seedu.address.model.wish;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents a the expiry date of a wish.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be of the FORMAT: dd/mm/yyyy";

    public static final String MESSAGE_DATE_OUTDATED = "Wish expiry date should be after current date";

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_FORMAT);

    public final String date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        boolean isDateValid = isValidDate(date);
        checkArgument(isDateValid, MESSAGE_DATE_CONSTRAINTS);
        this.date = date;

        assert(isDateValid);
    }

    public Date(Date date) {
        this(date.date);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            FORMAT.setLenient(false);
            java.util.Date date = FORMAT.parse(test);
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if date is greater than current date.
     */
    public static boolean isFutureDate(String test) throws ParseException {
        requireNonNull(test);
        java.util.Date date = FORMAT.parse(test);
        if (date.after(new java.util.Date())) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return A {@link java.util.Date} object of the current {@code Date}.
     */
    public java.util.Date getDateObject() {
        assert(isValidDate(date));

        java.util.Date dateObj;
        try {
            dateObj = FORMAT.parse(date);
        } catch (ParseException e) {
            dateObj = null;
        }

        assert(dateObj != null);

        return dateObj;
    }

    @Override
    public String toString() {
        return date;
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
