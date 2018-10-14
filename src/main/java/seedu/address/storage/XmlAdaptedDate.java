package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;

/**
 * JAXB-friendly adapted version of the Blocked Timeslot.
 */
public class XmlAdaptedDate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "The block-off Date's %s field is missing!";

    @XmlElement(required = true)
    private String week;
    @XmlElement(required = true)
    private String day;
    @XmlElement(required = true)
    private String time;



    /**
     * Constructs an XmlAdaptedDate.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedDate() {}

    /**
     * Constructs a {@code XmlAdaptedTimeBlock} with the given Date details.
     */
    public XmlAdaptedDate(String week, String day, String time) {
        this.week = week;
        this.day = day;
        this.time = time;
    }

    /**
     * Converts a given Date into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTimeBlock
     */
    public XmlAdaptedDate(Date source) {
        this.week = source.getWeek().toString();
        this.day = source.getDay().toString();
        this.time = source.getTime().toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Date object that is blocked out in the user's
     * timetable.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant
     */
    public Date toModelType() throws IllegalValueException {

        if (week == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Week.class.getSimpleName()));
        }

        if (!Week.isValidWeek(week)) {
            throw new IllegalValueException(Week.MESSAGE_WEEK_CONSTRAINTS);
        }

        if (day == null) {
            throw new IllegalValueException(Day.MESSAGE_DAY_CONSTRAINTS);
        }

        if (!Day.isValidDay(day)) {
            throw new IllegalValueException(Day.MESSAGE_DAY_CONSTRAINTS);
        }

        if (time == null) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }

        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }

        // Create the objects for creating a date.

        final Week modelWeek = new Week(week);
        final Day modelDay = new Day(day);
        final Time modelTime = new Time(time);

        return new Date(modelWeek, modelDay, modelTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTag)) {
            return false;
        }

        XmlAdaptedDate otherTime = (XmlAdaptedDate) other;
        return Objects.equals(week, otherTime.week)
                && Objects.equals(time, otherTime.time)
                && Objects.equals(day, otherTime.day);
    }
}
