package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's tutorial time slot in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */

public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should consist of the Day followed by a space and then the time of the tutorial. "
            + "Day should only contain alphabetical characters and it should not be blank."
            + "Time should consist of the start and end time of the tutorial in 24hour time format."
            + "Start time should only contain 4 numbers and it should not be blank."
            + "It will be followed by '-' and then the end time."
            + "End time should only contain 4 numbers and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * then the day should be entered first, followed by a whitespace,
     * then the start time followed by a -,
     * and lastly the end time of the tutorial.
     */
    public static final String DAY_PART_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";
    public static final String START_TIME_REGEX = "\\d{4}";
    public static final String END_TIME_REGEX = "\\d{4}";
    public static final String TIME_VALIDATION_REGEX = DAY_PART_REGEX + " "
            + START_TIME_REGEX + "-" + END_TIME_REGEX;

    public final String value;

    /**
     * Constructs an {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        value = time;
    }

    /**
     * Returns if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}