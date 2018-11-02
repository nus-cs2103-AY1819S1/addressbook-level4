package seedu.address.model.volunteer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Represents an Volunteer's birthday in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday dates can take in DD-MM-YYYY input, should be a valid date , and should not be blank";

    /*
     * First character of DD must be 0-3
     * First character of MM must be 0 or 1
     * Regex not enough to check for valid dates. Need to use a SimpleDateFormat parser as well.
     */
    public static final String BIRTHDAY_VALIDATION_REGEX = "[0-3]\\d-[01]\\d-\\d{4}";
    public static final int BIRTHDAY_VALIDATION_DIFFERENCE = 7;
    public static final int BIRTHDAY_MONTH_CORRECTION = 1;

    public final String value;

    /**
     * Constructs a {@code Birthday}.
     *
     * @param birthday A valid Birthday.
     */
    public Birthday(String birthday) {
        requireNonNull(birthday);
        checkArgument(isValidBirthday(birthday), MESSAGE_BIRTHDAY_CONSTRAINTS);
        checkArgument(isLessThanOrEqualToValidBirthday(birthday), MESSAGE_BIRTHDAY_CONSTRAINTS);
        value = birthday;
    }

    /**
     * Returns true if a given string is a valid birthday.
     */
    public static boolean isValidBirthday(String test) {
        if (!test.matches(BIRTHDAY_VALIDATION_REGEX)) {
            return false;
        }

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        df.setLenient(false);

        try {
            df.parse(test);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    /**
     * Returns true if current date falls on an earlier date or on the same date as the valid date.
     */
    public static boolean isLessThanOrEqualToValidBirthday(String test) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        String[] birthdayParts = test.split("-");
        //parseInt ignores leading zeros like 01 or 09 when converting from String to int
        int year = Integer.parseInt(birthdayParts[2]);
        int month = Integer.parseInt(birthdayParts[1]);
        int day = Integer.parseInt(birthdayParts[0]);

        int otherYear = calendar.get(Calendar.YEAR) - BIRTHDAY_VALIDATION_DIFFERENCE;
        int otherMonth = calendar.get(Calendar.MONTH) + BIRTHDAY_MONTH_CORRECTION;
        int otherDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (year > otherYear) {
            //start year is more than end year
            return false;
        } else if (year == otherYear && month > otherMonth) {
            //same year but start month is more than end month
            return false;
        } else if (year == otherYear && month == otherMonth && day > otherDay) {
            //same year, same month but start day is more than end day
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
