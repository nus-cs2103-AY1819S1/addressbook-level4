package seedu.address.commons.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility methods related to Dates
 */
public class DateUtil {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final String MESSAGE_DATE_CONSTRANTS = "Date should be of the format YYYY-MM-DD";

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
    public static LocalDate convertToDate(String dateString) throws DateTimeException {
        assert isValidDateFormat(dateString);
        return LocalDate.from(DATE_FORMAT.parse(dateString));
    }

    /**
     * Converts a date {@code LocalDate} into a {@code String}
     */
    public static String convertToString(LocalDate date) {
        return DATE_FORMAT.format(date);
    }

}
