package seedu.address.commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper functions for handling Date.
 */
public class DateUtil {

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date should be in the following format:"
            + "dd MMM yyyy, hh:mm AM/PM";
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

    /**
     * Formats the given date according to this format: 01 Jun 2016, 04:13 PM
     */
    public static String format(Date date) {
        return DATE_FORMAT.format(date);
    }

    /**
     * Formats the given string into a java {@code Date} object.
     * @throws ParseException When the given date in String does not conform to the above format.
     */
    public static Date parse(String date) throws ParseException {
        return DATE_FORMAT.parse(date);
    }
}
