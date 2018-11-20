package seedu.address.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents an assignment's deadline in the app.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class Deadline implements Comparable<Deadline> {

    public static final String FORMAT_STRING = "dd/MM/yyyy";
    public static final String MESSAGE_CONSTRAINTS =
            "Deadline should be in the format of " + FORMAT_STRING;
    public static final String VALIDATION_REGEX = "^(\\d{2})(\\/)(\\d{2})(\\/)(\\d{4})";

    public final String internalString;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param date A valid date string.
     */
    public Deadline(String date) {
        requireNonNull(date);
        checkArgument(isValid(date), MESSAGE_CONSTRAINTS);
        internalString = date;
    }

    /**
     * Returns true if a given string matches the class VALIDATION_REGEX.
     */
    public static boolean isValid(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(FORMAT_STRING);
            format.setLenient(false);
            try {
                format.parse(test);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public int compareTo(Deadline other) {
        return getValue().compareTo(other.getValue());
    }

    public Date getValue() {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_STRING);
        format.setLenient(false);
        try {
            Date date = format.parse(internalString);
            return date;
        } catch (Exception e) {
            return new Date(0);
        }
    }

    @Override
    public String toString() {
        return internalString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && internalString.equals(((Deadline) other).internalString)); // state check
    }
    @Override
    public int hashCode() {
        return internalString.hashCode();
    }


}
