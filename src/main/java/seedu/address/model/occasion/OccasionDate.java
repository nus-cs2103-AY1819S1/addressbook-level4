package seedu.address.model.occasion;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Occasions date in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidDate(String)}
 * @author kongzijin
 */
public class OccasionDate {

    public static final String MESSAGE_OCCASIONDATE_CONSTRAINTS = "Occasion "
        + "dates should be in the format YYYY-MM-DD, and should exist.";

    /*
     * The date should be in the format YYYY-MM-DD. The date value should be
     * valid.
     * TODO: Further constrain date to be a date in the future.
     */
    public static final String OCCASIONDATE_VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    public final String fullOccasionDate;

    /**
     * Empty Constructor.
     */
    public OccasionDate() {
        fullOccasionDate = "";
    }
    /**
     * Constructs a {@code OccasionDate}.
     *
     * @param date An occasion date.
     */
    public OccasionDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_OCCASIONDATE_CONSTRAINTS);
        fullOccasionDate = date;
    }

    /**
     * Make an identical deep copy of this OccasionDate.
     */
    public OccasionDate makeDeepDuplicate() {
        OccasionDate newDate = new OccasionDate(new String(fullOccasionDate));
        return newDate;
    }

    /**
     * Checks whether a given string is in correct format and is a valid date.
     *
     * @return A boolean value indicating the validation of this date.
     */

    public static boolean isValidDate(String test) {
        if (!test.matches(OCCASIONDATE_VALIDATION_REGEX)) {
            return false;
        }

        return isExistingDate(test);
    }

    /**
     * Checks whether the date is an existing date.
     * @param test
     * @return
     */
    public static boolean isExistingDate(String test) {
        Integer year = Integer.parseInt(test.substring(0, 4));
        Integer month = Integer.parseInt(test.substring(5, 7));
        Integer date = Integer.parseInt(test.substring(8, 10));

        if (year < 1000 || year > 9999) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }
        if (date < 1 || date > 31) {
            return false;
        }

        if (month == 2) {
            if (isLeapYear(year)) {
                return (date <= 29);
            } else {
                return (date <= 28);
            }
        }

        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return (date <= 30);
        }

        return true;
    }

    public static boolean isLeapYear (int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }

    @Override
    public String toString() {
        return fullOccasionDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof OccasionDate // instanceof handles nulls
            && fullOccasionDate.equals(((OccasionDate) other).fullOccasionDate));
    }

    @Override
    public int hashCode() {
        return fullOccasionDate.hashCode();
    }
}
