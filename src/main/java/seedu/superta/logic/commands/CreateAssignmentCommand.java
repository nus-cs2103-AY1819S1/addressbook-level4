package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_MARKS;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_TITLE;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;

import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.superta.model.tutorialgroup.exceptions.TutorialGroupNotFoundException;

/**
 * Command that creates an assignment.
 */
public class CreateAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "create-assignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an assignment.\n"
        + "Parameters: "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID "
        + PREFIX_ASSIGNMENT_TITLE + "ASSIGNMENT-TITLE "
        + PREFIX_ASSIGNMENT_MAX_MARKS + "MAXMARKS\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a "
        + PREFIX_ASSIGNMENT_TITLE + "Take Home Lab 1 "
        + PREFIX_ASSIGNMENT_MAX_MARKS + "40";

    public static final String MESSAGE_SUCCESS = "New assignment created: %1$s";
    public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the database.";

    private final Assignment toAdd;
    private final String tgId;

    public CreateAssignmentCommand(String tutorialGroupId, Assignment assignment) {
        requireNonNull(assignment);
        requireNonNull(tutorialGroupId);
        tgId = tutorialGroupId;
        toAdd = assignment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        try {
            model.addAssignment(tgId, toAdd);
        } catch (TutorialGroupNotFoundException e) {
            throw new CommandException("No such tutorial group.");
        } catch (DuplicateAssignmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
        }
        model.commitSuperTaClient();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CreateAssignmentCommand)) {
            return false;
        }

        //state check
        CreateAssignmentCommand e = (CreateAssignmentCommand) other;
        return toAdd.equals(e.toAdd)
                && tgId.equals(e.tgId);
    }
}
