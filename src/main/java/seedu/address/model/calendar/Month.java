package seedu.address.model.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author GilgameshTC
/**
 * Represents a month in the calendar.
 * Guarantees: immutable; is valid as declared in {@link #isValidMonthRegex(String)};
 * is valid as declared in {@link #isValidMonth(String)}
 */
public class Month {

    public static final String MESSAGE_MONTH_CONSTRAINTS =
            "Months should only be three characters long, and a valid month in the calendar. "
            + "It should also not be blank.";

    public static final String MONTH_VALIDATION_REGEX = "^[a-zA-Z]{3}$";

    // List of strings that represent valid months
    public static final String[] VALID_MONTHS = {
        "JAN",
        "FEB",
        "MAR",
        "APR",
        "MAY",
        "JUN",
        "JUL",
        "AUG",
        "SEP",
        "OCT",
        "NOV",
        "DEC"
    };

    public final String month;

    /**
     * Constructs a {@code Month}.
     *
     * @param month A valid month.
     */
    public Month(String month) {
        requireNonNull(month);
        checkArgument(isValidMonthRegex(month), MESSAGE_MONTH_CONSTRAINTS);
        month = month.toUpperCase();
        checkArgument(isValidMonth(month), MESSAGE_MONTH_CONSTRAINTS);
        this.month = month;
    }

    public Month() {
        // default create a JAN month obj
        // For Json parsing, have to have a default constructor
        this("JAN");
    }

    /**
     * Returns true if a given string follows the correct month regex.
     */
    public static boolean isValidMonthRegex(String test) {
        return test.matches(MONTH_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid month.
     */
    public static boolean isValidMonth(String test) {
        test = test.toUpperCase();
        boolean isValid = false;
        for (String monthName : VALID_MONTHS) {
            if (test.compareTo(monthName) == 0) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }

    /**
     * Returns a string listing all the valid months.
     */
    public static String listValidMonths() {
        StringBuilder sb = new StringBuilder();
        for (String month : VALID_MONTHS) {
            sb.append("\n" + month);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return month;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Month // instanceof handles nulls
                && month.equals(((Month) other).month)); // state check
    }

    @Override
    public int hashCode() {
        return month.hashCode();
    }
}
