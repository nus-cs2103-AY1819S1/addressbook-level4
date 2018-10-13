package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building CalendarEvent objects.
 */
public class CalendarEventBuilder {

    public static final String DEFAULT_TITLE = "CS2103 Lecture";
    public static final String DEFAULT_DESCRIPTION = "Abstraction, IntelliJ, Gradle";
    public static final String DEFAULT_VENUE = "123, Jurong West Ave 6, #08-111";

    private Title title;
    private Description description;
    private Venue venue;
    private Set<Tag> tags;

    public CalendarEventBuilder() {
        title = new Title(DEFAULT_TITLE);
        description = new Description(DEFAULT_DESCRIPTION);
        venue = new Venue(DEFAULT_VENUE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the CalendarEventBuilder with the data of {@code calendarEventToCopy}.
     */
    public CalendarEventBuilder(CalendarEvent calendarEventToCopy) {
        title = calendarEventToCopy.getTitle();
        description = calendarEventToCopy.getDescription();
        venue = calendarEventToCopy.getVenue();
        tags = new HashSet<>(calendarEventToCopy.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withTitle(String name) {
        this.title = new Title(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withTags(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }


    public CalendarEvent build() {
        return new CalendarEvent(title, description, venue, tags);
    }

}
