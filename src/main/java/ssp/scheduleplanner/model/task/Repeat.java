package ssp.scheduleplanner.model.task;

import static java.util.Objects.requireNonNull;

import static ssp.scheduleplanner.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of times a Task is to be repeated in Schedule Planner.
 */
public class Repeat {
    public static final String MESSAGE_REPEAT_CONSTRAINTS =
            "Repeat should be an integer greater than 1 but less than 16.\n"
            + "Where the number refers to the number of repetitions.\n";

    public static final String REPEAT_VALIDATION_REGEX = "\\b\\d{1,3}\\b";

    public final String value;

    public Repeat(String repeat) {
        requireNonNull(repeat);
        checkArgument(isValidRepeat(repeat), MESSAGE_REPEAT_CONSTRAINTS);
        value = repeat;
    }

    /**
     * Returns if a given string is a valid number of repetitions
     * @param test The number of repetitions in String format
     */
    public static boolean isValidRepeat(String test) {
        if (!test.matches(REPEAT_VALIDATION_REGEX)) {
            return false;
        }
        int intValue = Integer.parseInt(test);
        return intValue > 1 && intValue < 16;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Repeat
                && value.equals(((Repeat) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
