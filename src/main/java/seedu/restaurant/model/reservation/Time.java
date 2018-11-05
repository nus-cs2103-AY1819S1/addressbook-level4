package seedu.restaurant.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.TimeZone;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

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

        if (Time.canStrictParse(time)) {
            DateTimeFormatter validFormat =
                    DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN).withResolverStyle(ResolverStyle.STRICT);
            this.time = LocalTime.parse(time, validFormat);
        } else {
            Parser parser = new Parser(TimeZone.getTimeZone("GMT+8:00"));
            List<DateGroup> dateGroupList = parser.parse(time);

            this.time = dateGroupList.get(0).getDates().get(0)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        }
    }

    /**
     * Returns true if a given time string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return (Time.canStrictParse(test) || Time.canNattyParse(test));
    }

    /**
     * Returns true if a given time string can be regularly parsed without Natty.
     */
    public static boolean canStrictParse(String test) {
        DateTimeFormatter validFormat =
                DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN).withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalTime.parse(test, validFormat);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given time string can be parsed by Natty.
     */
    public static boolean canNattyParse(String test) {
        Parser parser = new Parser(TimeZone.getTimeZone("GMT+8:00"));
        List<DateGroup> dateGroupList = parser.parse(test);

        if (dateGroupList.isEmpty()) {
            return false;
        }

        List<java.util.Date> dates = dateGroupList.get(0).getDates();

        return !dates.isEmpty();
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
