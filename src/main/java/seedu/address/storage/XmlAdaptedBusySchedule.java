package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.UniqueSchedule;
import seedu.address.model.user.Username;

/**
 * JAXB-friendly adapted version of the user's list of busy Dates..
 */
public class XmlAdaptedBusySchedule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "The BusyDates' %s field is missing!";

    @XmlElement(required = true)
    private String username; // user that the blocked date belongs to.

    @XmlElement
    private List<XmlAdaptedDate> xmlDates = new ArrayList<XmlAdaptedDate>();

    /**
     * Constructs an XmlAdaptedBusyDates.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBusySchedule() {}

    /**
     * Constructs a {@code XmlAdaptedBusyDates} with the given username and dates.
     */
    public XmlAdaptedBusySchedule(String username, List<XmlAdaptedDate> dates) {
        this.username = username;
        if (dates != null) {
            this.xmlDates = new ArrayList<>(dates);
        }
    }


    /**
     * Converts a given user's UniqueSchedule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedBusyDate.
     */
    public XmlAdaptedBusySchedule(UniqueSchedule source) {
        this.username = source.getUsername().toString();
        this.xmlDates = retrieveDateList(source);
    }

    /**
     * Helper method to obtain a XMLAdaptedDate list from a UniqueSchedule
     */
    private List<XmlAdaptedDate> retrieveDateList(UniqueSchedule source) {

        List<Date> retrievedDates = new ArrayList<>(source.getAllBlockedDatesOnSchedule());

        List<XmlAdaptedDate> xmlDateList = retrievedDates.stream()
                .map(XmlAdaptedDate::new)
                .collect(Collectors.toList());

        return xmlDateList;
    }


    /**
     * Converts this jaxb-friendly adapted busy schedule object into the model's UniqueSchedule  object
     * that functions as the user's personal timetable.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant
     */
    public UniqueSchedule toModelType() throws IllegalValueException {
        final List<Date> allDates = new ArrayList<>();
        for (XmlAdaptedDate date : xmlDates) {
            allDates.add(date.toModelType());
        }

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }

        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }

        final Username modelUsername = new Username(username);

        UniqueSchedule schedule = new UniqueSchedule(modelUsername);
        // add all dates.
        allDates.stream().forEach(schedule::add);
        return schedule;
    }

}
