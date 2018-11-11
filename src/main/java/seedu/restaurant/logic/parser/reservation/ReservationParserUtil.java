package seedu.restaurant.logic.parser.reservation;

import static java.util.Objects.requireNonNull;

import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.reservation.Date;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Time;

//@@author m4dkip
/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ReservationParserUtil {

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String pax} into a {@code Pax}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code pax} is invalid.
     */
    public static Pax parsePax(String pax) throws ParseException {
        requireNonNull(pax);
        String trimmedPax = pax.trim();
        if (!Pax.isValidPax(trimmedPax)) {
            throw new ParseException(Pax.MESSAGE_PAX_CONSTRAINTS);
        }
        return new Pax(trimmedPax);
    }

    /**
     * Parses a {@code String date} into a {@code Date}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        if (Date.isPassed(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_DATE_PASSED);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code String time} into a {@code Time}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Time.isValidTime(trimmedTime)) {
            throw new ParseException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        return new Time(trimmedTime);
    }
}
