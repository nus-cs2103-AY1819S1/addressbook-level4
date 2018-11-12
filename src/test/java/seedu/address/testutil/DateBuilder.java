package seedu.address.testutil;

import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;

/**
 * A utility class to help with building
 */
public class DateBuilder {
    public static final String DEFAULT_WEEK = "recess";
    public static final String DEFAULT_DAY = "tue";
    public static final String DEFAULT_TIME = "1800";

    private Week week;
    private Day day;
    private Time time;

    public DateBuilder() {
        week = new Week(DEFAULT_WEEK);
        day = new Day(DEFAULT_DAY);
        time = new Time(DEFAULT_TIME);
    }

    /**
     * Sets the {@code Week} of the {@code Date} that we are building.
     */
    public DateBuilder withWeek(String week) {
        this.week = new Week(week);
        return this;
    }

    /**
     * Sets the {@code Week} of the {@code Date} that we are building.
     */
    public DateBuilder withDay(String day) {
        this.day = new Day(day);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Date} that we are building.
     */
    public DateBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    public Date build() {
        return new Date(week, day, time);
    }

}
