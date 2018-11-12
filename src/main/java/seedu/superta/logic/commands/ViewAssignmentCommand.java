package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;

import java.util.Optional;

import seedu.superta.commons.core.EventsCenter;
import seedu.superta.commons.events.ui.AssignmentSelectedEvent;
import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.Title;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler

/**
 * Command to view assignment details.
 */
public class ViewAssignmentCommand extends Command {
    public static final String COMMAND_WORD = "view-assignment";
    public static final String MESSAGE_SUCCESS = "Assignment Details: \n%s";
    public static final String MESSAGE_TUTORIAL_GROUP_NOT_FOUND = "Tutorial group not found.";
    public static final String MESSAGE_ASSIGNMENT_NOT_FOUND = "Assignment not found.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Retrieves detailed assignment information.\n"
        + "Parameters: "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID "
        + PREFIX_GENERAL_ASSIGNMENT_TITLE + "ASSIGNMENT-TITLE\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a "
        + PREFIX_GENERAL_ASSIGNMENT_TITLE + "lab1";

    private final String tutorialGroupId;
    private final Title title;

    public ViewAssignmentCommand(String tutorialGroupId, Title title) {
        requireAllNonNull(tutorialGroupId, title);
        this.tutorialGroupId = tutorialGroupId;
        this.title = title;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Optional<TutorialGroup> optionalTutorialGroup = model.getTutorialGroup(tutorialGroupId);
        if (!optionalTutorialGroup.isPresent()) {
            throw new CommandException(MESSAGE_TUTORIAL_GROUP_NOT_FOUND);
        }
        TutorialGroup tutorialGroup = optionalTutorialGroup.get();
        Optional<Assignment> optionalAssignment = tutorialGroup.getAssignment(title);
        if (!optionalAssignment.isPresent()) {
            throw new CommandException(MESSAGE_ASSIGNMENT_NOT_FOUND);
        }
        Assignment assignment = optionalAssignment.get();
        StringBuilder details = new StringBuilder("");
        details.append("Title: " + assignment.getTitle().assignmentTitle + "\n");
        details.append("Maximum Marks: " + assignment.getMaxMarks() + "\n");
        details.append("Average: " + String.format("%.2f\n", assignment.getAverage()));
        details.append("Median: " + String.format("%.2f\n", assignment.getMedian()));
        details.append("Projected difficulty: " + String.format("%.2f\n", assignment.getProjectedDifficulty()));
        details.append("Grade book: \n");
        assignment.getGradebook().stream().forEach(
            entry -> {
                details.append(entry.studentId + ": ");
                details.append(entry.marks);
                details.append("\n");
            }
        );

        EventsCenter.getInstance().post(new AssignmentSelectedEvent(tutorialGroup, assignment));
        return new CommandResult(details.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewAssignmentCommand)) {
            return false;
        }

        ViewAssignmentCommand command = (ViewAssignmentCommand) other;
        return this.tutorialGroupId.equals(command.tutorialGroupId)
            && this.title.equals(command.title);
    }
}
