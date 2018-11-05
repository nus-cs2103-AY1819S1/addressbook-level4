package seedu.restaurant.model.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
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
 * Represents a date in the calendar in DD-MM-YYYY format Guarantees: immutable; is valid as declared in {@link
 * #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should only contain numbers, and it should be in the format DD-MM-YYYY.\nThe date must exist in "
                    + "the calendar";
    public static final String DATE_FORMAT_PATTERN = "dd-MM-uuuu";

    private final LocalDate date;

    /**
     * Constructs a {@code date}.
     *
     * !!!!!!!!!!!! IMPORTANT NOTE !!!!!!!!!!!!!
     * Firstly checks if the date is able to be regularly parsed in DD-MM-YYYY format without using NATTY. This is
     * necessary as NATTY cannot be configured to interpret dates in DD-MM-YYYY format, and will default to MM-DD-YYYY.
     *
     * i.e. NATTY will think 03-12-2018 means "12th of March" instead of "3rd of December"
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);

        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);

        /*
        if (Date.canStrictParse(date)) {
            DateTimeFormatter validFormat =
                    DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN).withResolverStyle(ResolverStyle.STRICT);
            this.date = LocalDate.parse(date, validFormat);
        } else {
            Parser parser = new Parser(TimeZone.getTimeZone("GMT+8:00"));
            List<DateGroup> dateGroupList = parser.parse(date);

            this.date = dateGroupList.get(0).getDates().get(0)
                    .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        */

        DateTimeFormatter validFormat =
                DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN).withResolverStyle(ResolverStyle.STRICT);
        this.date = LocalDate.parse(date, validFormat);
    }

    /**
     * Returns true if a given date string is a valid date.
     */
    public static boolean isValidDate(String test) {
        //return (Date.canStrictParse(test) || Date.canNattyParse(test));
        return Date.canStrictParse(test);
    }

    /**
     * Returns true if a given date string can be regularly parsed.
     */
    public static boolean canStrictParse(String test) {
        DateTimeFormatter validFormat =
                DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN).withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(test, validFormat);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given date string can be parsed by Natty.
     */
    public static boolean canNattyParse(String test) {
        Parser parser = new Parser(TimeZone.getTimeZone("GMT+8:00"));
        List<DateGroup> dateGroupList = parser.parse(test);

        if (dateGroupList.isEmpty()) {
            return false;
        }

        List<java.util.Date> dates = dateGroupList.get(0).getDates();

        return !dates.isEmpty(); //hard to understand but codacy rejected the readable version :/
    }


    public LocalDate getValue() {
        return date;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN).withResolverStyle(ResolverStyle.STRICT));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                    && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
