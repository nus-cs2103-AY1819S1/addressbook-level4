package seedu.scheduler.testutil;

import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_TYPE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_UNTIL_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Set;

import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.logic.commands.EditCommand.EditEventDescriptor;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.tag.Tag;

/**
 * A utility class for Event.
 */
public class EventUtil {

    /**
     * Returns an add command string for adding the {@code event}.
     */
    public static String getAddCommand(Event event) {
        return AddCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(Event event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_EVENT_NAME + event.getEventName().value + " ");
        sb.append(PREFIX_START_DATE_TIME + event.getStartDateTime().getPrettyString() + " ");
        sb.append(PREFIX_END_DATE_TIME + event.getEndDateTime().getPrettyString() + " ");
        sb.append(PREFIX_DESCRIPTION + event.getDescription().value + " ");
        sb.append(PREFIX_VENUE + event.getVenue().value + " ");
        sb.append(PREFIX_REPEAT_TYPE + event.getRepeatType().name() + " ");
        sb.append(PREFIX_REPEAT_UNTIL_DATE_TIME + event.getRepeatUntilDateTime().getPrettyString() + " ");
        sb.append(event.getReminderDurationList().getPrettyString() + " ");
        event.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditEventDescriptor}'s details.
     */
    public static String getEditEventDescriptorDetails(EditEventDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getEventName().ifPresent(eventName -> sb.append(PREFIX_EVENT_NAME)
                .append(eventName.value).append(" "));
        descriptor.getStartDateTime().ifPresent(startDateTime -> sb.append(PREFIX_START_DATE_TIME)
                .append(startDateTime.getPrettyString()).append(" "));
        descriptor.getEndDateTime().ifPresent(endDateTime -> sb.append(PREFIX_END_DATE_TIME)
                .append(endDateTime.getPrettyString()).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_DESCRIPTION)
                .append(description.value).append(" "));
        descriptor.getVenue().ifPresent(venue -> sb.append(PREFIX_VENUE)
                .append(venue.value).append(" "));
        descriptor.getRepeatType().ifPresent(repeatType -> sb.append(PREFIX_REPEAT_TYPE)
                .append(repeatType.name()).append(" "));
        descriptor.getRepeatUntilDateTime().ifPresent(repeatUntilDateTime -> sb.append(PREFIX_REPEAT_UNTIL_DATE_TIME)
                .append(repeatUntilDateTime.getPrettyString()).append(" "));
        sb.append(descriptor.getReminderDurationList().get().getPrettyString()).append(" ");
        /*System.out.println(descriptor.getReminderDurationList().get().get());
        descriptor.getReminderDurationList().ifPresent(reminderDurationList ->
                sb.append(reminderDurationList.getPrettyString()).append(" "));*/
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
