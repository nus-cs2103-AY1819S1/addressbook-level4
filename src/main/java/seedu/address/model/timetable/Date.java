package seedu.address.model.timetable;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a date in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Date implements Comparable<Date> {

    // Data fields
    private final Week week;
    private final Day day;
    private final Time time;

    /**
     * Every field must be present and not null.
     */
    public Date(Week week, Day day, Time time) {
        requireAllNonNull(week, day, time);
        this.week = week;
        this.day = day;
        this.time = time;
    }

    public Week getWeek() {
        return week;
    }

    public Day getDay() {
        return day;
    }

    public Time getTime() {
        return time;
    }


    /**
     * Returns true if both dates have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return otherDate.getWeek().equals(this.week)
                && otherDate.getDay().equals(this.day)
                && otherDate.getTime().equals(this.time);
    }

    /**
     * Generates a random valid Date.
     * @return the valid date.
     */
    public static Date generateRandomDate() {
        return new Date(Week.generateRandomWeek(), Day.generateRandomDay(), Time.generateRandomTime());
    }

    /**
     * Generates a random list of valid dates, all unique.
     * @param number of dates to generate.
     * @return the list of unique valid dates.
     */
    public static List<Date> generateRandomUniqueDates(int number) {

        List<Date> uniqueDateList = new ArrayList<>();

        while (number > 0) {
            Date generatedDate = generateRandomDate();
            if (uniqueDateList.contains(generatedDate)) {
                continue;
            }

            uniqueDateList.add(generatedDate);
            number--;
        }

        return uniqueDateList;
    }

    @Override
    public int hashCode() {
        return Objects.hash(week, day, time);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[NUS Week: ")
                .append(getWeek())
                .append(" Day:")
                .append(getDay())
                .append(" Time:")
                .append(getTime())
                .append("]");
        return builder.toString();
    }

    @Override
    public int compareTo(Date other) {
        int result = this.week.compareTo(other.week);
        if (result != 0) {
            return result;
        }

        result = this.day.compareTo(other.day);
        if (result != 0) {
            return result;
        }

        return this.time.compareTo(other.time);
    }
}
