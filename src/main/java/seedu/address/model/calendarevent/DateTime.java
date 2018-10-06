package seedu.address.model.calendarevent;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.LocalDateTime;

/**
 * Represents a Calendar Event's datetime fields in the scheduler.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(int, int, int, int, int)}
 */
public class DateTime {

    private LocalDateTime date;

    /**
     * Constructs a {@code DateTime}
     * Wrapper class for LocalDateTime
     *
     * @param year   A valid year
     * @param month  A valid month
     * @param day    A valid day
     * @param hour   A valid hour
     * @param minute A valid minute
     */
    public DateTime(int year, int month, int day, int hour, int minute) throws DateTimeException {
        requireAllNonNull(year, month, day, hour, minute);
        if (year <= 0) {
            throw new DateTimeException("Invalid year");
        }
        date = LocalDateTime.of(year, month, day, hour, minute);
    }

    public int getYear() {
        return date.getYear();
    }

    public int getMonth() {
        return date.getMonthValue();
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public int getHour() {
        return date.getHour();
    }

    public int getMinute() {
        return date.getMinute();
    }

    public void setYear(int year) throws DateTimeException {
        date = date.withYear(year);
    }

    public void setMonth(int month) throws DateTimeException {
        date = date.withMonth(month);
    }

    public void setDay(int day) throws DateTimeException {
        date = date.withDayOfMonth(day);
    }

    public void setHour(int hour) throws DateTimeException {
        date = date.withHour(hour);
    }

    public void setMinute(int minute) {
        date = date.withMinute(minute);
    }

    @Override
    public String toString() {
        return date.toString();
    }

    /**
     * Returns if a given datetime is a valid datetime.
     */
    public static boolean isValidDateTime(int year, int month, int day, int hour, int minute) {
        try {
            DateTime dateTime = new DateTime(year, month, day, hour, minute);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
