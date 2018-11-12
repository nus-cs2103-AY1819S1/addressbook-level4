package seedu.superta.logic.commands;

import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

/**
 * Command which deletes an existing assignment from a tutorial group from the SuperTA client.
 */
public class DeleteAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "delete-assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an assignment from a tutorial group.\n"
            + "Parameters: "
            + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID "
            + PREFIX_GENERAL_ASSIGNMENT_TITLE + "ASSIGNMENT-TITLE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a "
            + PREFIX_GENERAL_ASSIGNMENT_TITLE + "Lab 1";

    public static final String MESSAGE_SUCCESS = "Assignment %s from %s deleted.";
    public static final String MESSAGE_FAILURE_TG = "No such tutorial group.";
    public static final String MESSAGE_FAILURE_ASS = "No such assignment.";

    private final String assToDelete;
    private final String fromTutorialGroup;

    public DeleteAssignmentCommand(String assToDelete, String fromTutorialGroup) {
        this.assToDelete = assToDelete;
        this.fromTutorialGroup = fromTutorialGroup;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireAllNonNull(model, assToDelete, fromTutorialGroup);

        try {
            model.deleteAssignment(fromTutorialGroup, assToDelete);
        } catch (TutorialGroupNotFoundException e) {
            throw new CommandException(MESSAGE_FAILURE_TG);
        } catch (AssignmentNotFoundException e) {
            throw new CommandException(MESSAGE_FAILURE_ASS);
        }

        model.commitSuperTaClient();

        return new CommandResult(String.format(MESSAGE_SUCCESS, assToDelete, fromTutorialGroup));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAssignmentCommand)) {
            return false;
        }

        //state check
        DeleteAssignmentCommand e = (DeleteAssignmentCommand) other;
        return assToDelete.equals(e.assToDelete)
                && fromTutorialGroup.equals(e.fromTutorialGroup);
    }
}
