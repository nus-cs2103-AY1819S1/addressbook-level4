package seedu.address.model.appointment;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

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
        checkArgument(isValidDay(day, month, year), MESSAGE_DAY_CONSTRAINTS);
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
    public static boolean isValidDay(int day, int month, int year) {
        if (day <= 0 || day > 31) {
            return false;
        }
        if (month == 2 && day > 30 && isLeapYear(year)) { //if leap year
            return false;
        }
        if (month == 2 && day > 28 && !isLeapYear(year)) {
            return false;
        }
        if (month % 2 == 0 && month < 9 && day > 30) { //before september
            return false;
        }
        if (month % 2 == 1 && month >= 9 && day > 30) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if it is a Gregorian leap year.
     * @param year The year to check if leap year.
     */
    public static boolean isLeapYear(int year) {
        //Solution below adapted from https://en.wikipedia.org/wiki/Leap_year
        if (year % 4 != 0) {
            return false;
        }
        else if (year % 100 != 0) {
            return true;
        }
        else if (year % 400 != 0) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Returns true if such a month exists.
     * @param month The month to validate.
     * @return Validity of month.
     */
    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    /**
     * Returns true if such a year exists.
     * @param year The year to validate.
     * @return Validity of month.
     */
    public static boolean isValidYear(int year) {
        String string = String.valueOf(year);
        return string.matches(YEAR_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        Date otherDate = (Date) other;
        return (otherDate.getDay() == getDay())
                && (otherDate.getMonth() == getMonth())
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
