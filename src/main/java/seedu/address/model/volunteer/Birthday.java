package seedu.address.model.volunteer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * Represents an Volunteer's birthday in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday dates can take in DD-MM-YYYY input, should be a valid date , and should not be blank";

    /*
     * First character of DD must be 0-3
     * First character of MM must be 0 or 1
     * Regex not enough to check for valid dates. Need to use a SimpleDateFormat parser as well.
     */
    public static final String BIRTHDAY_VALIDATION_REGEX = "[0-3]\\d-[01]\\d-\\d{4}";

    public final String value;

    /**
     * Constructs a {@code Birthday}.
     *
     * @param birthday A valid Birthday.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_BIRTHDAY_CONSTRAINTS);
        value = birthday;
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String test) {
        if (!test.matches(BIRTHDAY_VALIDATION_REGEX)) {
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

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
