package ssp.scheduleplanner.model.task;

import static java.util.Objects.requireNonNull;
import static ssp.scheduleplanner.commons.util.AppUtil.checkArgument;

import java.util.Calendar;

/**
 * Represents a Task's date in the Schedule Planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date format should be: ddmmyy \nExample: 191219 will be 19th Dec, 2019\n"
            + "Please make sure your date is valid ^ ^ \n";
    public static final String DATE_VALIDATION_REGEX = "\\d{6}";
    public final String value;
    public final int yymmdd;
    public final Calendar calendar;
    public final String displayDate;
    public final String[] monthNames = {
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        value = date;

        String dayString = date.substring(0, 2);
        int day = Integer.parseInt(dayString);
        String monthString = date.substring(2, 4);
        int month = Integer.parseInt(monthString);
        String yearString = date.substring(4, 6);
        int year = Integer.parseInt(yearString) + 2000;

        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);

        yymmdd = Integer.parseInt(yearString + monthString + dayString);

        displayDate = dayString + " " + monthNames[month - 1] + " " + year;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (!test.matches(DATE_VALIDATION_REGEX)) {
            return false;
        }
        String dayString = test.substring(0, 2);
        int day = Integer.parseInt(dayString);
        String monthString = test.substring(2, 4);
        int month = Integer.parseInt(monthString);
        String yearString = test.substring(4, 6);
        int year = Integer.parseInt(yearString) + 2000;
        if (month > 12 || month == 0) {
            return false;
        } else if (day == 0) {
            return false;
        } else if (isLargeMonth(month)) {
            return day <= 31;
        } else if (month == 2) {
            if (isLeapYear(year)) {
                return day <= 29;
            } else {
                return day <= 28;
            }
        } else {
            return day <= 30;
        }
    }

    /**
     * Returns true if a given year is a leap year.
     * @param year A year
     */
    private static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 400 == 0) {
            return true;
        } else if (year % 100 == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns true if a given month has 31 days.
     * @param month A month
     */
    private static boolean isLargeMonth(int month) {
        return month == 1 || month == 3 || month == 5
                || month == 7 || month == 8
                || month == 10 || month == 12;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Compare two dates.
     * @param a
     * @param b
     * @return -1 if a is an earlier date than b, 0 if a and b are equal,
     * 1 if a is a later date than b.
     */
    public static int compare(Date a, Date b) {
        if (a.yymmdd < b.yymmdd) {
            return -1;
        } else if (a.yymmdd == b.yymmdd) {
            return 0;
        } else {
            return 1;
        }
    }

}
