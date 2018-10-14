package seedu.address.storage;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Name;
import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;

/**
 * JAXB-friendly version of the User.
 */
public class XmlAdaptedJio {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "jio's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String week;
    @XmlElement(required = true)
    private String day;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String address;

    /**
     * Constructs an XmlAdaptedJio.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJio() {}

    /**
     * Constructs an {@code XmlAdaptedJio} with the given jio details.
     */
    public XmlAdaptedJio(String name, String week, String day, String time, String address) {
        this.name = name;
        this.week = week;
        this.day = day;
        this.time = time;
        this.address = address;
    }

    /**
     * Converts a given Jio into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdadptedJio
     */
    public XmlAdaptedJio(Jio source) {
        name = source.getName().toString();
        week = source.getDate().getWeek().toString();
        day = source.getDate().getDay().toString();
        time = source.getDate().getTime().toString();
        address = source.getLocation().toString();
    }

    /**
     * Converts this jaxb-friendly adapted jio object into the model's Jio object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant
     */
    public Jio toModelType() throws IllegalValueException {
        // Check for name
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        // Check for date
        if (week == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Week.class.getSimpleName()));
        }
        if (!Week.isValidWeek(week)) {
            throw new IllegalValueException(Week.MESSAGE_WEEK_CONSTRAINTS);
        }
        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Day.class.getSimpleName()));
        }
        if (!Day.isValidDay(day)) {
            throw new IllegalValueException(Day.MESSAGE_DAY_CONSTRAINTS);
        }
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Date modelDate = new Date(new Week(week), new Day(day), new Time(time));

        // Check for address
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        return new Jio(modelName, modelDate, modelAddress);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUser)) {
            return false;
        }

        XmlAdaptedJio otherJio = (XmlAdaptedJio) other;
        return Objects.equals(name, otherJio.name)
                && Objects.equals(week, otherJio.week)
                && Objects.equals(day, otherJio.day)
                && Objects.equals(time, otherJio.time)
                && Objects.equals(address, otherJio.address);
    }
}



