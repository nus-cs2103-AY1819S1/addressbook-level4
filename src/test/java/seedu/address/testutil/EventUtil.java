package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT_UNTIL_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

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
        sb.append(PREFIX_START_DATE_TIME + event.getStartDateTime().value.toString() + " ");
        sb.append(PREFIX_END_DATE_TIME + event.getEndDateTime().value.toString() + " ");
        sb.append(PREFIX_DESCRIPTION + event.getDescription().value + " ");
        sb.append(PREFIX_VENUE + event.getVenue().value + " ");
        sb.append(PREFIX_PRIORITY + event.getPriority().toString() + " ");
        sb.append(PREFIX_REPEAT_TYPE + event.getRepeatType().toString() + " ");
        sb.append(PREFIX_REPEAT_UNTIL_DATE_TIME + event.getRepeatUntilDateTime().value.toString() + " ");
        event.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_EVENT_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_DESCRIPTION).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_DESCRIPTION).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
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
