package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import com.google.common.collect.Ordering;

/**
 * Represents a Week from the NUS academic calendar.
 * Guarantees: immutable; is valid as declared in {@Link isValidWeek(String)}
 */
public class Week implements Comparable<Week> {

    public static final String MESSAGE_WEEK_CONSTRAINTS =
            "Week should only be one of the following: 1, 2, 3, 4, 5, 6, "
            + "recess, 7, 8, 9, 10, 11, 12, 13, reading, 14, 15";

    public static final String WEEK_VALIDATION_REGEX =
            "[1-9]|1[0-5]|recess|reading";

    /** Provides the ordering of the weeks for compareTo */
    private static final Ordering<String> weekOrdering = Ordering.explicit(
            "1", "2", "3", "4", "5", "6", "recess", "7",
            "8", "9", "10", "11", "12", "13", "reading", "14", "15");

    public final String value;


    /**
     * Constructs a {@code Week}.
     *
     * @param week A valid NUS week.
     */
    public Week(String week) {
        requireNonNull(week);
        checkArgument(isValidWeek(week), MESSAGE_WEEK_CONSTRAINTS);
        value = week;
    }

    /**
     * Returns true if a given string is a valid NUS week.
     */
    public static boolean isValidWeek(String test) {
        return test.matches(WEEK_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Week // instanceof handles nulls
                && value.equals(((Week) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Week other) {
        return weekOrdering.compare(this.value, other.value);
    }
}
