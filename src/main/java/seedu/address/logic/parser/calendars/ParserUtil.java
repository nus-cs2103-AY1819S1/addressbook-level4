package seedu.address.logic.parser.calendars;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_YEAR = "Year must be a non-negative integer.";
    public static final String MESSAGE_INVALID_MONTH = "Month must be an integer between 1 and 12";

    /**
     * Parses {@code yearString} into an {@code int} and returns it. Leading and
     * trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified year is invalid (not a non-negative
     *                        integer).
     */
    public static int parseYear(String yearString) throws ParseException {
        String trimmedYearString = yearString.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedYearString)) {
            throw new ParseException(MESSAGE_INVALID_YEAR);
        }
        return Integer.parseInt(trimmedYearString);
    }

    /**
     * Parses {@code monthString} into an {@code int} and returns it. Leading and
     * trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified month is invalid (<1 or > 12).
     */
    public static int parseMonth(String monthString) throws ParseException {
        String trimmedMonthString = monthString.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedMonthString)) {
            throw new ParseException(MESSAGE_INVALID_MONTH);
        }

        int month = Integer.parseInt(trimmedMonthString);

        if (month < 1 || month > 12) {
            throw new ParseException(MESSAGE_INVALID_MONTH);
        }

        return month;
    }
}
