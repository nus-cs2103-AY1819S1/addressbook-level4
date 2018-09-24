package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Location;
import seedu.address.model.event.Name;
import seedu.address.model.event.Time;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;


public class EventBuilder {
    public static final String DEFAULT_NAME = "Blood Donation Drive 2018";
    public static final String DEFAULT_LOCATION = "750E, Chai Chee Road, #08-111";
    public static final String DEFAULT_START_DATE = "02-10-2018";
    public static final String DEFAULT_END_DATE = "05-10-2018";
    public static final String DEFAULT_START_TIME = "11:30";
    public static final String DEFAULT_END_TIME = "17:30";
    public static final String DEFAULT_DESCRIPTION = "Donation drive for blood.";

    private Name name;
    private Location location;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private Description description;
    private Set<Tag> tags;

    public EventBuilder() {
        name = new Name(DEFAULT_NAME);
        location = new Location(DEFAULT_LOCATION);
        startDate = new Date(DEFAULT_START_DATE);
        endDate = new Date(DEFAULT_END_DATE);
        startTime = new Time(DEFAULT_START_TIME);
        endTime = new Time(DEFAULT_END_TIME);
        description = new Description(DEFAULT_DESCRIPTION);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        location = eventToCopy.getLocation();
        startDate = eventToCopy.getStartDate();
        endDate = eventToCopy.getEndDate();
        startTime = eventToCopy.getStartTime();
        endTime = eventToCopy.getEndTime();
        description = eventToCopy.getDescription();
        tags = new HashSet<>(eventToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    /**
     * Sets the start {@code Date} of the {@code Event} that we are building.
     */
    public EventBuilder withStartDate(String startDate) {
        this.startDate = new Date(startDate);
        return this;
    }
    /**
     * Sets the end {@code Date} of the {@code Event} that we are building.
     */
    public EventBuilder withEndDate(String endDate) {
        this.endDate = new Date(endDate);
        return this;
    }

    /**
     * Sets the start {@code Time} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(String startTime) {
        this.startTime = new Time(startTime);
        return this;
    }

    /**
     * Sets the end {@code Time} of the {@code Event} that we are building.
     */
    public EventBuilder withEndTime(String endTime) {
        this.endTime = new Time(endTime);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Event build() {
        return new Event(name, location, startDate, endDate, startTime, endTime, description, tags);
    }

}
