package seedu.address.model.appointment;

import java.util.Objects;

/**
 * Represents the time in military time to be used in appointment.
 */
public class Time {
    private final int hour;
    private final int minute;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof Time) {
            return false;
        }
        Time otherTime = (Time) other;
        return (otherTime.getHour() == getHour()) && (otherTime.getMinute() == getMinute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, minute);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Time: ")
                .append(getHour())
                .append(" ")
                .append(getMinute());
        return builder.toString();
    }
}
