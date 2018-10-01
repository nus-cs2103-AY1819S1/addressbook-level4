package seedu.address.model.appointment;

import java.util.Objects;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 *  Represents the date in dd/mm/yyyy format to be used in appointment.
 */
public class Date {
    public static final String MESSAGE_DAY_CONSTRAINTS =
            "The day should be numeric and not exceed the number of days in specified month."
                    + "It should not be blank.";
    public static final String MESSAGE_MONTH_CONSTRAINTS =
            "The month should be numeric (1 starting in Jan) and not exceed the number of months in a year."
                    + "It should not be blank.";
    public static final String MESSAGE_YEAR_CONSTRAINTS =
            "The month should be numeric and it should not be blank.";
    public static final String YEAR_VALIDATION_REGEX = "\\d{4}";

    private final int day;
    private final int month;
    private final int year;

    /**
     * Constructs a {@code Date}.
     * @param day A valid day.
     * @param month A valid month.
     * @param year A valid year.
     */
    public Date(int day, int month, int year) {
        requireAllNonNull(day, month, year);
        checkArgument(isValidDay(day, month), MESSAGE_DAY_CONSTRAINTS);
        checkArgument(isValidMonth(month), MESSAGE_MONTH_CONSTRAINTS);
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    /**
     * Returns true if such a day exists.
     * @param day The day to validate.
     * @param month Determines validity of day.
     * @return Validity of day.
     */
    public boolean isValidDay(int day, int month) {
        if (day <= 0 || day > 31) {
            return false;
        }
        if (month == 2) {
            if (day > 29) { //february has 28/29 days
                return false;
            }
        }
        if (month % 2 == 0 && month != 8) { //excluding august which has 31 days
            if (day > 30) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if such a month exists.
     * @param month The month to validate.
     * @return Validity of month.
     */
    public boolean isValidMonth(int month) {
        if (month >= 1 && month <= 12) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if such a year exists.
     * @param year The year to validate.
     * @return Validity of month.
     */
    public boolean isValidYear(int year) {
        String string = String.valueOf(year);
        return string.matches(YEAR_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Date) {
            return false;
        }
        Date otherDate = (Date) other;
        return (otherDate.getDay() == getDay()) && (otherDate.getMonth() == getMonth())
                && (otherDate.getYear() == getYear());
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDay())
                .append("/")
                .append(getMonth())
                .append("/")
                .append(getYear());
        return builder.toString();
    }
}
