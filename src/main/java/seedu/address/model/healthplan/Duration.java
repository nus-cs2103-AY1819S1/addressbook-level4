package seedu.address.model.healthplan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


public class Duration {
    public static final String MESSAGE_DURATION_CONSTRAINTS =
            "Duration should only contain numbers";
    public static final String DURATION_VALIDATION_REGEX = "\\d{1,}";
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
