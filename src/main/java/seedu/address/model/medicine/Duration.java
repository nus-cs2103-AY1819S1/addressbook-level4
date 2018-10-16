package seedu.address.model.medicine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author snajef
/**
 * POJO class to hold duration-related information.
 *
 * @author Darien Chong
 *
 */
public class Duration {
    public static final int DAYS_PER_WEEK = 7;
    public static final int DAYS_PER_MONTH = 30;
    public static final int DAYS_PER_YEAR = 365;
    public static final String DATE_FORMAT_PATTERN = "dd-MM-yyyy";
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
    public static final String MESSAGE_DURATION_MUST_BE_POSITIVE = "Duration must be a positive value!";

    private int durationInDays;

    /**
     * The convention adopted here is that the duration in days should be equal
     * to the duration between the start date and end date, inclusive of the end date.
     */
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Standard c'tor, takes in duration in days.
     */
    public Duration(int days) throws IllegalValueException {
        if (days <= 0) {
            throw new IllegalValueException(MESSAGE_DURATION_MUST_BE_POSITIVE);
        }
        durationInDays = days;
        startDate = LocalDate.now();
        endDate = LocalDate.now().plus(durationInDays - 1, ChronoUnit.DAYS);
    }

    /**
     * Defensive copy constructor.
     */
    public Duration(Duration d) throws IllegalValueException {
        this(d.getDurationInDays());
    }

    /**
     * Returns the duration in weeks (rounded down).
     */
    public static int getAsWeeks(int days) {
        return (int) Math.floor(days / DAYS_PER_WEEK);
    }

    /**
     * Returns the duration in months (rounded down).
     */
    public static int getAsMonths(int days) {
        return (int) Math.floor(days / DAYS_PER_MONTH);
    }

    /**
     * Returns the duration in years (rounded down).
     */
    public static int getAsYears(int days) {
        return (int) Math.floor(days / DAYS_PER_YEAR);
    }

    /**
     * Returns the duration in days.
     * This is what should be used for comparison of durations.
     */
    public int getDurationInDays() {
        return durationInDays;
    }

    /**
     * Shifts the date range of the duration. Maintains the same duration in
     * milliseconds.
     * @param newStartDate The new start date to use.
     */
    public void shiftDateRange(LocalDate newStartDate) {
        // We don't have to be defensive here, because LocalDate objects are immutable.
        startDate = newStartDate;
        endDate = newStartDate.plus(getDurationInDays() - 1, ChronoUnit.DAYS);
    }

    /**
     * Returns the start date as a {@code String} in dd-MM-yyyy format.
     */
    public String getStartDateAsString() {
        return startDate.format(DATE_FORMAT);
    }

    /**
     * Returns the end date as a {@code String} in dd-MM-yyyy format.
     */
    public String getEndDateAsString() {
        return endDate.format(DATE_FORMAT);
    }

    /**
     * Returns the duration in the following format:
     *      [W year(s),] [X month(s),] [Y week(s),] [Z day(s)]
     * with parts omitted if the value is equal to 0.
     *
     * e.g.
     * 14 days -> 2 week(s)
     * 31 days -> 1 month(s), 1 day(s)
     */
    public String getDurationAsString() {
        StringBuilder duration = new StringBuilder();
        int d = durationInDays;

        assert d >= 0;

        if (getAsYears(d) >= 1) {
            duration.append(getAsYears(d)).append(" year(s),");
            d -= getAsYears(d) * DAYS_PER_YEAR;
        }

        assert d >= 0;

        if (getAsMonths(d) >= 1) {
            duration.append(" ").append(getAsMonths(d)).append(" month(s),");
            d -= getAsMonths(d) * DAYS_PER_MONTH;
        }

        assert d >= 0;

        if (getAsWeeks(d) >= 1) {
            duration.append(" ").append(getAsWeeks(d)).append(" week(s),");
            d -= getAsWeeks(d) * DAYS_PER_WEEK;
        }

        assert d >= 0;

        if (d >= 1) {
            duration.append(" ").append(d).append(" day(s),");
        }

        // Remove trailing comma
        String result = duration.toString().trim();
        result = result.substring(0, result.length() - 1);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder fromTo = new StringBuilder();
        fromTo.append("from ").append(getStartDateAsString()).append(" to ").append(getEndDateAsString());

        return fromTo.toString() + " [" + getDurationAsString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Duration) {
            Duration d = (Duration) o;
            return startDate.equals(d.startDate)
                && endDate.equals(d.endDate);
        }

        return false;
    }
}
