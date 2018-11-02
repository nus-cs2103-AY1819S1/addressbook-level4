package seedu.meeting.model.meeting;

import java.time.Month;
import java.time.Year;

/* @@author NyxF4ll */
/**
 * An utility class to check consistency of a date.
 */
public class DateUtil {

    private static final Year MINIMUM_YEAR = Year.of(0);
    private static final Year MAXIMUM_YEAR = Year.of(10000);
    private static final int MINIMUM_HOUR_OF_DAY = 0;
    private static final int MAXIMUM_HOUR_OF_DAY = 23;
    private static final int MINIMUM_MINUTE_OF_HOUR = 0;
    private static final int MAXIMUM_MINUTE_OF_HOUR = 59;

    private DateUtil() {} // prevent instantiation

    private static Integer getLength(Month month, Year year) {
        switch(month) {
        case FEBRUARY:
            return year.isLeap() ? 29 : 28;
        case APRIL:
        case JUNE:
        case SEPTEMBER:
        case NOVEMBER:
            return 30;
        default:
            return 31;
        }
    }

    /**
     * Returns true if the date is consistent with the length of the month it belongs to.
     */
    public static boolean isDateConsistent(Integer date, Month month, Year year) {
        Integer monthLength = getLength(month, year);

        return date > 0 && date <= monthLength;
    }

    /**
     * Returns true if the {@code year} is between 1 and 9999.
     */
    public static boolean isYearWithinBounds(Year year) {
        return year.isAfter(MINIMUM_YEAR) && year.isBefore(MAXIMUM_YEAR);
    }

    /**
     * Returns true if the {@code hour} is between 0 and 23.
     */
    public static boolean isHourWithinBounds(Integer hour) {
        return hour >= MINIMUM_HOUR_OF_DAY && hour <= MAXIMUM_HOUR_OF_DAY;
    }

    /**
     * Returns true if the {@code minute} is between 0 and 59.
     */
    public static boolean isMinuteWithinBounds(Integer minute) {
        return minute >= MINIMUM_MINUTE_OF_HOUR && minute <= MAXIMUM_MINUTE_OF_HOUR;
    }
}
