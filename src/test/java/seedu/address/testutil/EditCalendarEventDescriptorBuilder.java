package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditEventCommand.EditCalendarEventDescriptor;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditCalendarEventDescriptor objects.
 */
public class EditCalendarEventDescriptorBuilder {

    private EditCalendarEventDescriptor descriptor;

    public EditCalendarEventDescriptorBuilder() {
        descriptor = new EditCalendarEventDescriptor();
    }

    public EditCalendarEventDescriptorBuilder(EditCalendarEventDescriptor descriptor) {
        this.descriptor = new EditCalendarEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCalendarEventDescriptor} with fields containing {@code calendarevent}'s details
     */
    public EditCalendarEventDescriptorBuilder(CalendarEvent calendarEvent) {
        descriptor = new EditCalendarEventDescriptor();
        descriptor.setTitle(calendarEvent.getTitle());
        descriptor.setDescription(calendarEvent.getDescriptionObject());
        descriptor.setStart(calendarEvent.getStart());
        descriptor.setEnd(calendarEvent.getEnd());
        descriptor.setVenue(calendarEvent.getVenue());
        descriptor.setTags(calendarEvent.getTags());
    }

    /**
     * Sets the {@code Title} of the {@code EditCalendarEventDescriptor} that we are building.
     */
    public EditCalendarEventDescriptorBuilder withTitle(String name) {
        descriptor.setTitle(new Title(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCalendarEventDescriptor} that we are building.
     */
    public EditCalendarEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the start {@code DateTime} of the {@code EditCalendarEventDescriptor} that we are building.
     */
    public EditCalendarEventDescriptorBuilder withStart(String start) {
        descriptor.setStart(new DateTime(start));
        return this;
    }

    /**
     * Sets the end {@code DateTime} of the {@code EditCalendarEventDescriptor} that we are building.
     */
    public EditCalendarEventDescriptorBuilder withEnd(String end) {
        descriptor.setEnd(new DateTime(end));
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code EditCalendarEventDescriptor} that we are building.
     */
    public EditCalendarEventDescriptorBuilder withVenue(String address) {
        descriptor.setVenue(new Venue(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCalendarEventDescriptor}
     * that we are building.
     */
    public EditCalendarEventDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCalendarEventDescriptor build() {
        return descriptor;
    }
}
