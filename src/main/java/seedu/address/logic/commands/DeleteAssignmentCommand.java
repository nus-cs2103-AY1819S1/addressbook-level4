package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.permission.Permission;
import seedu.address.model.project.Assignment;

/**
 * Deletes a person identified using it's displayed index from the assignment list.
 */
public class DeleteAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "deleteassignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the assignment identified by the index number used in the displayed assignment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ASSIGNMENT_SUCCESS = "Deleted Assignment: %1$s";

    private final Index targetIndex;

    public DeleteAssignmentCommand(Index targetIndex) {
        requiredPermission.addPermissions(Permission.REMOVE_EMPLOYEE);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult runBody(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Assignment> lastShownList = model.getFilteredAssignmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        Assignment assignmentToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.deleteAssignment(assignmentToDelete);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_DELETE_ASSIGNMENT_SUCCESS, assignmentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAssignmentCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteAssignmentCommand) other).targetIndex)); // state check
    }
}
