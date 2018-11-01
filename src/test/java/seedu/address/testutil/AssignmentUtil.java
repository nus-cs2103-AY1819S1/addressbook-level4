package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;

import seedu.address.logic.commands.AddAssignmentCommand;
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
    /*public static String getEditAssignmentDescriptorDetails(EditCommand.EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getSalary().ifPresent(address -> sb.append(PREFIX_SALARY).append(address.value).append(" "));
        if (descriptor.getProjects().isPresent()) {
            Set<Project> projects = descriptor.getProjects().get();
            if (projects.isEmpty()) {
                sb.append(PREFIX_PROJECT);
            } else {
                projects.forEach(s -> sb.append(PREFIX_PROJECT).append(s.getProjectName()).append(" "));
            }
        }
        return sb.toString();
    }*/
}
