package seedu.address.testutil;

import seedu.address.model.group.Date;
import seedu.address.model.group.Description;
import seedu.address.model.group.Meeting;
import seedu.address.model.group.Title;
import seedu.address.model.person.Address;

/**
 * A utility class to help with building Meeting object.
 */
public class MeetingBuilder {

    public static final String DEFAULT_TITLE = "Weekly meetup";
    public static final String DEFAULT_DATE = "22-02-2017";
    public static final String DEFAULT_LOCATION = "COM1-0202";
    public static final String DEFAULT_DESCRIPTION = "To discuss the individual progress";

    private Title title;
    private Date date;
    private Address location;
    private Description description;

    public MeetingBuilder() {
        title = new Title(DEFAULT_TITLE);
        date = new Date(DEFAULT_DATE);
        location = new Address(DEFAULT_LOCATION);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the MeetingBuilder with the data of {@code meetingToCopy}.
     */
    public MeetingBuilder(Meeting meetingToCopy) {
        title = meetingToCopy.getTitle();
        date = meetingToCopy.getDate();
        location = meetingToCopy.getLocation();
        description = meetingToCopy.getDescription();
    }

    /**
     * Sets the {@code Title} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withLocation(String location) {
        this.location = new Address(location);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Meeting} that we are building.
     */
    public MeetingBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Build a new meeting from MeetingBuilder
     *
     * @return The new meeting
     */
    public Meeting build() {
        Meeting meeting = new Meeting(title, date, location, description);
        return meeting;
    }
}
