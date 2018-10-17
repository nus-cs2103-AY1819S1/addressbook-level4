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
            "Date should be of the format: dd/mm/yyyy";

    public static final String MESSAGE_DATE_OUTDATED = "Wish expiry date should be after current date";

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    private static final SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

    public final String date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            format.setLenient(false);
            java.util.Date date = format.parse(test);
        } catch (ParseException pe) {
            System.out.println("Cannot parse date");
            return false;
        }
        return true;
    }

    /*
     * Returns true if date is greater than current date.
     */
    public static boolean isFutureDate(String test) throws ParseException {
        java.util.Date date = format.parse(test);
        if (date.after(new java.util.Date())) {
            return true;
        }
        return false;
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
