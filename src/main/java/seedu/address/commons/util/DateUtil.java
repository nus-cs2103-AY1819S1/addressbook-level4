package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * Utility methods related to Dates
 */
public class DateUtil {
    public static final String DATE_FORMAT = "yyyy-mm-dd";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final String DATE_REGEX = "[0-9]{4,4}-[0-9]{2,2}-[0-9]{2,2}"; // Valid date format

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isValidDateFormat(String dateString) {
        return dateString.matches(DATE_REGEX);
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     * @param dateString A valid string representing a date according to {@code DATE_FORMAT}
     */
    public static Date format(String dateString) throws ParseException {
        assert dateString.matches(DATE_REGEX);
        return simpleDateFormat.parse(dateString);
    }
}
