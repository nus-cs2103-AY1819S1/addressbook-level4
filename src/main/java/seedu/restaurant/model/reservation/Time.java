package seedu.restaurant.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

//@@author m4dkip
/**
 * Represents a time in HH:MM format Guarantees: immutable; is valid as declared in {@link
 * #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Times should only contain numbers, and it should be in the format HH:MM.\nThe time must be a valid 24h "
                    + "time.";
    public static final String TIME_FORMAT_PATTERN = "HH:mm";

    private final LocalTime time;

    /**
     * Constructs a {@code time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);

        /*
        Parser parser = new Parser(TimeZone.getTimeZone("GMT+8:00"));
        List<DateGroup> dateGroupList = parser.parse(time);

        this.time = dateGroupList.get(0).getDates().get(0)
                .toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        */

        this.time = LocalTime.parse(time);
    }

    /**
     * Returns true if a given time string is a valid time.
     */
    public static boolean isValidTime(String test) {
        /*
        Parser parser = new Parser(TimeZone.getTimeZone("GMT+8:00"));
        List<DateGroup> dateGroupList = parser.parse(test);

        if (dateGroupList.isEmpty()) {
            return false;
        }

        List<java.util.Date> dates = dateGroupList.get(0).getDates();

        return !dates.isEmpty(); //hard to understand but codacy rejected the readable version :/
        */

        DateTimeFormatter validFormat =
                DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN).withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalTime.parse(test, validFormat);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public LocalTime getValue() {
        return time;
    }

    @Override
    public String toString() {
        return time.toString();
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
}
