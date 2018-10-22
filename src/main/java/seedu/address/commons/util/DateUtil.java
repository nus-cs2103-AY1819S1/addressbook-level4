package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Utility methods related to Dates
 */
public class DateUtil {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Checks if a date string is of the valid {@code DATE_FORMAT}
     */
    public static boolean isValidDateFormat(String dateString) {
        try {
            DATE_FORMAT.parse(dateString);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Converts a date {@code String} into a {@code Date}
     * @param dateString A valid string representing a date according to {@code DATE_FORMAT}
     */
    public static LocalDateTime convertToDate(String dateString) throws ParseException {
        assert isValidDateFormat(dateString);
        return LocalDateTime.from(DATE_FORMAT.parse(dateString));
    }

    /**
     * Converts a date {@code Date} into a {@code String}
     */
    public static String convertToString(LocalDateTime date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * Checks if two dates have equivalent year, month, day, hour, minutes, and seconds
     */
//    public static boolean dateEquals(Date first, Date second) {
//        Calendar.getInstance()
//        return first.to
//    }
}
