package seedu.scheduler.testutil;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.scheduler.logic.commands.EditCommand.EditEventDescriptor;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setEventName(event.getEventName());
        descriptor.setStartDateTime(event.getStartDateTime());
        descriptor.setEndDateTime(event.getEndDateTime());
        descriptor.setDescription(event.getDescription());
        descriptor.setVenue(event.getVenue());
        descriptor.setRepeatType(event.getRepeatType());
        descriptor.setRepeatUntilDateTime(event.getRepeatUntilDateTime());
        descriptor.setTags(event.getTags());
        descriptor.setReminderDurationList(event.getReminderDurationList());
    }

    /**
     * Sets the {@code EventName} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventName(String eventName) {
        descriptor.setEventName(new EventName(eventName));
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withStartDateTime(LocalDateTime startDateTime) {
        descriptor.setStartDateTime(new DateTime(startDateTime));
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEndDateTime(LocalDateTime endDateTime) {
        descriptor.setEndDateTime(new DateTime(endDateTime));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withVenue(String venue) {
        descriptor.setVenue(new Venue(venue));
        return this;
    }

    /**
     * Sets the {@code RepeatType} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withRepeatType(RepeatType repeatType) {
        descriptor.setRepeatType(repeatType);
        return this;
    }

    /**
     * Sets the {@code RepeatUntilDateTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withRepeatUntilDateTime(LocalDateTime repeatUntilDateTime) {
        descriptor.setRepeatUntilDateTime(new DateTime(repeatUntilDateTime));
        return this;
    }

    /**
     * Sets the {@code ReminderDurationList} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withReminderDurationList(ReminderDurationList reminderDurationList) {
        descriptor.setReminderDurationList(reminderDurationList);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditEventDescriptor}
     * that we are building.
     */
    public EditEventDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }

}
