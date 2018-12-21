package seedu.address.model.attendance;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.model.common.Validator;

/**
 * Represents an assignment's deadline in the app.
 * Guarantees: immutable; is valid as declared in {@link #isValid(String)}
 */
public class SessionDate extends Validator implements Comparable<SessionDate> {

    public static final String FORMAT_STRING = "dd/MM/yyyy";
    public static final String MESSAGE_CONSTRAINTS =
            "Deadline should be in the format of " + FORMAT_STRING;
    public static final String VALIDATION_REGEX = "^(\\d{2})(\\/)(\\d{2})(\\/)(\\d{4})";

    /**
     * Constructs a {@code Deadline}.
     *
     * @param date A valid date string.
     */
    public SessionDate(String date) {
        super(date);
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

    public int compareTo(SessionDate other) {
        return this.getValue().compareTo(other.getValue());
    }

    @Override
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

}
