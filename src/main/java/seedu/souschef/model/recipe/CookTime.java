package seedu.souschef.model.recipe;

import static seedu.souschef.commons.util.AppUtil.checkArgument;

import java.time.Duration;

/**
 * Represents a Recipe's cook time in the application content.
 * Guarantees: immutable; is valid as declared in {@link #isValidCookTime(String)}
 */
public class CookTime {


    public static final String MESSAGE_COOKTIME_CONSTRAINTS =
            "Cook time should only contain unit H/M/S or H & M or M & S, and a value before each unit";
    public static final String COOKTIME_VALIDATION_REGEX =
            "PT(((\\d+H)(\\d+M))|((\\d+M)(\\d+S))|(\\d+H)|(\\d+M)|(\\d+S))";
    public final Duration value;

    /**
     * Constructs a {@code CookTime}.
     *
     * @param duration A valid cook time.
     */
    public CookTime(String duration) {
        if (duration == null) {
            duration = "PT0M";
        }
        checkArgument(isValidCookTime(duration), MESSAGE_COOKTIME_CONSTRAINTS);
        value = Duration.parse(duration);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidCookTime(String time) {
        return time.matches(COOKTIME_VALIDATION_REGEX);
    }

    public String getDisplayableCookTime() {
        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookTime // instanceof handles nulls
                && value.equals(((CookTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
