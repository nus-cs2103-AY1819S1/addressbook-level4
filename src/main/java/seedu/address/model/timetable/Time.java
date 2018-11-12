package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the time in a 24 hour format, with 30 minute intervals permitted only.
 * Guarantees: immutable; is valid as declared in {@Link #isValidTime(String)}
 */
public class Time implements Comparable<Time> {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time should only be in the 24-hour format in 30 minute"
            + "intervals, without any colons. THe time should fall between 0600 and 2330."
            + "For example: 2330, 0600, 0130";

    /**
     * Regular expression used to test whether the time falls between 0600 and 2330.
     * Time must be in 30 minute intervals.
     */
    public static final String TIME_VALIDATION_REGEX = "(0[6-9]|1\\d|2[0-3])([03]0)";

    private static final List<Time> ALL_POSSIBLE_TIMES = Time.generateAllPossibleTimes();

    public final String time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Creates all the possible times and returns it in an unmodifiable list.
     * @return the unmodifiable list of all possible time configurations, sorted.
     */
    private static List<Time> generateAllPossibleTimes() {

        List<Time> allPossibleTimes = new ArrayList<>();

        for (int hour = 6; hour <= 23; hour++) {
            for (int minute = 0; minute <= 59; minute += 30) {
                DecimalFormat formatter = new DecimalFormat("00");
                String hourFormatted = formatter.format(hour);
                String minuteFormatted = formatter.format(minute);
                Time time = new Time(hourFormatted + minuteFormatted);
                allPossibleTimes.add(time);
            }
        }
        // defensive programming.
        Collections.sort(allPossibleTimes);

        return Collections.unmodifiableList(allPossibleTimes);
    }

    /**
     * Generates a random time among the allowed possible times.
     * @return the random time generated.
     */
    public static Time generateRandomTime() {
        return ALL_POSSIBLE_TIMES.get((int) Math.floor(Math.random() * ALL_POSSIBLE_TIMES.size()));
    }

    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    /**
     * Makes the Time class comparable, with earlier times preceding the later times.
     * @param other the other Time.
     */
    @Override
    public int compareTo(Time other) {
        return this.time.compareTo(other.time);
    }
}
