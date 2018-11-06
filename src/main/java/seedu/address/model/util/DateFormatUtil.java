package seedu.address.model.util;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Contains utility methods to handle date time strings needed for {@code Task}.
 */
public class DateFormatUtil {
    public static final String DATE_FORMAT_MINIMAL = "dd-MM-yy";
    public static final String DATE_FORMAT_STANDARD = "dd-MM-yy HHmm";
    public static final String DATE_FORMAT_MINIMAL_REGEX = "\\d{1,2}-\\d{1,2}-\\d{2,4}";
    public static final String DATE_FORMAT_STANDARD_REGEX = "\\d{1,2}-\\d{1,2}-\\d{2,4} \\d{4}";

    public static final SimpleDateFormat FORMAT_MINIMAL;
    public static final SimpleDateFormat FORMAT_STANDARD;

    static {
        FORMAT_MINIMAL = createDateFormat(DATE_FORMAT_MINIMAL);
        FORMAT_STANDARD = createDateFormat(DATE_FORMAT_STANDARD);
    }

    /**
     * Returns date from date string.
     * {@link #DATE_FORMAT_MINIMAL}.{@link #DATE_FORMAT_STANDARD}
     *
     * @param date string of date
     * @return Date object from date string, else null
     */
    public static Date parseDate(String date) {
        Date result = null;
        try {
            if (isValidDateStandardFormat(date)) {
                result = FORMAT_STANDARD.parse(date);
            } else if (isValidDateMinimalFormat(date)) {
                result = FORMAT_MINIMAL.parse(date);
            }
        } catch (Exception e) {
            result = null;
        }
        return result;
    }

    /**
     * Returns true if a given string is one of two valid date formats.
     * {@link #DATE_FORMAT_MINIMAL}.{@link #DATE_FORMAT_STANDARD}
     *
     * @param test string to be tested against the format
     * @return true if is the correct format. False otherwise
     */
    public static boolean isValidDateFormat(String test) {
        return isValidDateMinimalFormat(test)
                || isValidDateStandardFormat(test);
    }

    private static boolean isValidDateMinimalFormat(String test) {
        return isValidDateFormatFromTemplate(test, FORMAT_MINIMAL, DATE_FORMAT_MINIMAL_REGEX);
    }

    private static boolean isValidDateStandardFormat(String test) {
        return isValidDateFormatFromTemplate(test, FORMAT_STANDARD, DATE_FORMAT_STANDARD_REGEX);
    }

    /**
     * Returns true if a given string is a valid date format of template.
     *
     * @param test string to be tested against the format
     * @param format format to test string with
     * @return true if is the correct format. False otherwise
     */
    private static boolean isValidDateFormatFromTemplate(String test, SimpleDateFormat format, String regex) {
        try {
            format.parse(test);
        } catch (Exception e) {
            return false;
        }
        return test.matches(regex);
    }

    /**
     * Creates a SimpleDateFormat for the given date format.
     * setLenient(false) is needed for stricter date time parsing
     */
    private static SimpleDateFormat createDateFormat(String template) {
        SimpleDateFormat format = new SimpleDateFormat(template);
        format.setLenient(false);
        return format;
    }

}
