package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.model.project.Assignment;

/**
 * A utility class for Assignment.
 */
public class AssignmentUtil {
    /**
     * Returns an add command string for adding the {@code assignment}.
     */
    public static String getAddAssignmentCommand(Assignment assignment) {
        return AddAssignmentCommand.COMMAND_WORD + " " + getAssignmentDetails(assignment);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getAssignmentDetails(Assignment assignment) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ASSIGNMENT_NAME + assignment.getProjectName().fullProjectName + " ");
        sb.append(PREFIX_AUTHOR + assignment.getAuthor().fullName + " ");
        sb.append(PREFIX_ASSIGNMENT_DESCRIPTION + assignment.getDescription().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditAssignmentDescriptor}'s details.
     */
    public static String getEditAssignmentDescriptorDetails(EditAssignmentCommand.EditAssignmentDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getAssignmentName().ifPresent(assignmentName -> sb.append(PREFIX_ASSIGNMENT_NAME)
                .append(assignmentName.fullProjectName).append(" "));
        descriptor.getAuthor().ifPresent(name -> sb.append(PREFIX_AUTHOR).append(name.fullName).append(" "));
        descriptor.getDescription().ifPresent(description -> sb.append(PREFIX_ASSIGNMENT_DESCRIPTION)
                .append(description.value).append(" "));
        return sb.toString();
    }
}
