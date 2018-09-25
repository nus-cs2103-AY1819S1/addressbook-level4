package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY_VALUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.person.Task;
import seedu.address.model.tag.Label;

/**
 * A utility class for Task.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Task person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Task person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_DUE_DATE + person.getDueDate().value + " ");
        sb.append(PREFIX_PRIORITY_VALUE + person.getPriorityValue().value + " ");
        sb.append(PREFIX_DESCRIPTION + person.getDescription().value + " ");
        person.getLabels().stream().forEach(
            s -> sb.append(PREFIX_LABEL + s.labelName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditTaskDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditCommand.EditTaskDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getDueDate().ifPresent(phone -> sb.append(PREFIX_DUE_DATE).append(phone.value).append(" "));
        descriptor.getPriorityValue().ifPresent(email -> sb.append(PREFIX_PRIORITY_VALUE).append(email.value).append(" "));
        descriptor.getDescription().ifPresent(address -> sb.append(PREFIX_DESCRIPTION).append(address.value).append(" "));
        if (descriptor.getLabels().isPresent()) {
            Set<Label> tags = descriptor.getLabels().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_LABEL);
            } else {
                tags.forEach(s -> sb.append(PREFIX_LABEL).append(s.labelName).append(" "));
            }
        }
        return sb.toString();
    }
}
