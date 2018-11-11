package seedu.souschef.model.healthplan;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.AppUtil.checkArgument;

/**
 * Class to handle the duration component of health plans
 * duration class
 */
public class Duration {
    public static final String MESSAGE_DURATION_CONSTRAINTS =
            "Duration should only contain non-zero and non-negative numbers";
    public static final String DURATION_VALIDATION_REGEX = "^[1-9]\\d*$";
    public final String value;


    public Duration(String duration) {
        requireNonNull(duration);
        checkArgument(isValidDuration(duration), MESSAGE_DURATION_CONSTRAINTS);
        value = duration;
    }

    public static boolean isValidDuration(String test) {
        return test.matches(DURATION_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Duration // instanceof handles nulls
                && value.equals(((Duration) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }






}
